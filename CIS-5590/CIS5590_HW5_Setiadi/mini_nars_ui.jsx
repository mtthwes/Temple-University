import { useState, useRef, useEffect, useCallback } from "react";

/* ── Mini-NARS Engine (JS) ────────────────────────────────────────── */

const K = 1;
const and_ = (...a) => a.reduce((x, y) => x * y, 1);
const or_ = (...a) => 1 - a.reduce((x, y) => y * (1 - x), 1);
const not_ = (x) => 1 - x;
const exp_ = (f, c) => c * (f - 0.5) + 0.5;
const toEv = (f, c) => { const w = (K * c) / (1 - c); return [w * f, w]; };
const fromEv = (wp, w) => [w > 0 ? wp / w : 0.5, w / (w + K)];

const TF = {
  ded: (f1, c1, f2, c2) => [and_(f1, f2), and_(f1, c1, f2, c2)],
  abd: (f1, c1, f2, c2) => fromEv(and_(f1, f2, c1, c2), and_(f1, c1, c2)),
  ind: (f1, c1, f2, c2) => fromEv(and_(f1, f2, c1, c2), and_(f2, c1, c2)),
  exe: (f1, c1, f2, c2) => fromEv(and_(f1, f2, c1, c2), and_(f1, f2, c1, c2)),
  rev: (f1, c1, f2, c2) => {
    const [wp1, w1] = toEv(f1, c1);
    const [wp2, w2] = toEv(f2, c2);
    return fromEv(wp1 + wp2, w1 + w2);
  },
};

class NarsEngine {
  constructor() { this.reset(); }
  reset() {
    this.beliefs = new Map();
    this.concepts = new Set();
    this.log = [];
    this.cycle = 0;
    this.serial = 0;
  }
  addBelief(sub, pred, f, c) {
    const key = `${sub}->${pred}`;
    const old = this.beliefs.get(key);
    if (old && old.eb !== this.serial) {
      const [rf, rc] = TF.rev(old.f, old.c, f, c);
      this.beliefs.set(key, { sub, pred, f: rf, c: rc, eb: this.serial });
      this.log.push({ cy: this.cycle, type: "REV", text: `<${sub} --> ${pred}>. %${rf.toFixed(2)};${rc.toFixed(2)}%` });
    } else if (!old) {
      this.beliefs.set(key, { sub, pred, f, c, eb: this.serial });
    }
    this.concepts.add(sub);
    this.concepts.add(pred);
  }
  input(text) {
    text = text.trim();
    if (!text) return;
    this.serial++;
    const isQ = text.endsWith("?");
    let tvMatch = text.match(/%([0-9.]+);([0-9.]+)%/);
    let f = 1.0, c = 0.9;
    if (tvMatch) { f = parseFloat(tvMatch[1]); c = parseFloat(tvMatch[2]); }
    let stMatch = text.match(/<\s*([^\s]+)\s+-->\s+([^\s>]+)\s*>/);
    if (!stMatch) { this.log.push({ cy: this.cycle, type: "ERR", text: "Parse error" }); return; }
    const sub = stMatch[1], pred = stMatch[2];
    if (isQ) {
      this.log.push({ cy: this.cycle, type: "IN", text: `<${sub} --> ${pred}>?` });
      this.answerQuestion(sub, pred);
    } else {
      this.log.push({ cy: this.cycle, type: "IN", text: `<${sub} --> ${pred}>. %${f.toFixed(2)};${c.toFixed(2)}%` });
      this.addBelief(sub, pred, f, Math.min(c, 0.999));
    }
  }
  answerQuestion(sub, pred) {
    if (sub === "?" || pred === "?") {
      let best = null;
      for (const bl of this.beliefs.values()) {
        if ((sub === "?" && bl.pred === pred) || (pred === "?" && bl.sub === sub)) {
          if (!best || exp_(bl.f, bl.c) > exp_(best.f, best.c)) best = bl;
        }
      }
      if (best) this.log.push({ cy: this.cycle, type: "ANS", text: `<${best.sub} --> ${best.pred}>. %${best.f.toFixed(2)};${best.c.toFixed(2)}%` });
      else this.log.push({ cy: this.cycle, type: "ANS", text: "No answer found" });
      return;
    }
    const b = this.beliefs.get(`${sub}->${pred}`);
    if (b) {
      this.log.push({ cy: this.cycle, type: "ANS", text: `<${b.sub} --> ${b.pred}>. %${b.f.toFixed(2)};${b.c.toFixed(2)}%` });
    } else {
      this.log.push({ cy: this.cycle, type: "ANS", text: "No direct answer. Run more cycles." });
    }
  }
  runCycles(n) {
    const derived = new Set();
    for (let i = 0; i < n; i++) {
      this.cycle++;
      const entries = Array.from(this.beliefs.values());
      if (entries.length < 2) continue;
      const i1 = Math.floor(Math.random() * entries.length);
      let i2 = Math.floor(Math.random() * entries.length);
      if (i2 === i1) i2 = (i2 + 1) % entries.length;
      for (const r of this.syllogism(entries[i1], entries[i2])) {
        const dk = `${r.sub}->${r.pred}`;
        if (dk === `${entries[i1].sub}->${entries[i1].pred}`) continue;
        if (dk === `${entries[i2].sub}->${entries[i2].pred}`) continue;
        if (r.sub === r.pred) continue;
        if (!derived.has(dk) && r.c > 0.05) {
          this.addBelief(r.sub, r.pred, r.f, Math.min(r.c, 0.999));
          derived.add(dk);
          this.log.push({ cy: this.cycle, type: "OUT", text: `<${r.sub} --> ${r.pred}>. %${r.f.toFixed(2)};${r.c.toFixed(2)}%  [${r.rule}]` });
        }
      }
    }
  }
  syllogism(b1, b2) {
    const results = [];
    const { sub: s1, pred: p1, f: f1, c: c1 } = b1;
    const { sub: s2, pred: p2, f: f2, c: c2 } = b2;
    if (s1 === p2 && p1 !== s2) {
      const [f, c] = TF.ded(f1, c1, f2, c2);
      results.push({ sub: s2, pred: p1, f, c, rule: "deduction" });
      const [fe, ce] = TF.exe(f1, c1, f2, c2);
      results.push({ sub: p1, pred: s2, f: fe, c: ce, rule: "exemplification" });
    }
    if (p1 === p2 && s1 !== s2) {
      const [f, c] = TF.abd(f1, c1, f2, c2);
      results.push({ sub: s2, pred: s1, f, c, rule: "abduction" });
    }
    if (s1 === s2 && p1 !== p2) {
      const [f, c] = TF.ind(f1, c1, f2, c2);
      results.push({ sub: p2, pred: p1, f, c, rule: "induction" });
    }
    if (p1 === s2 && s1 !== p2) {
      const [f, c] = TF.ded(f2, c2, f1, c1);
      results.push({ sub: s1, pred: p2, f, c, rule: "deduction" });
      const [fe, ce] = TF.exe(f2, c2, f1, c1);
      results.push({ sub: p2, pred: s1, f: fe, c: ce, rule: "exemplification" });
    }
    return results;
  }
  getBeliefs() { return Array.from(this.beliefs.values()).sort((a, b) => exp_(b.f, b.c) - exp_(a.f, a.c)); }
  getConcepts() { return Array.from(this.concepts); }
}

/* ── Concept Graph ────────────────────────────────────────────────── */

function ConceptGraph({ beliefs, concepts }) {
  const ref = useRef(null);
  useEffect(() => {
    const c = ref.current;
    if (!c) return;
    const ctx = c.getContext("2d");
    const W = c.width, H = c.height;
    ctx.clearRect(0, 0, W, H);
    ctx.fillStyle = "#fafafa";
    ctx.fillRect(0, 0, W, H);
    if (concepts.length === 0) return;
    const pos = {};
    const cx = W / 2, cy = H / 2, r = Math.min(W, H) * 0.34;
    concepts.forEach((t, i) => {
      const a = (2 * Math.PI * i) / concepts.length - Math.PI / 2;
      pos[t] = { x: cx + r * Math.cos(a), y: cy + r * Math.sin(a) };
    });
    beliefs.forEach((b) => {
      const from = pos[b.sub], to = pos[b.pred];
      if (!from || !to) return;
      ctx.strokeStyle = b.f > 0.5 ? `rgba(0,0,0,${0.15 + b.c * 0.6})` : `rgba(180,0,0,${0.2 + b.c * 0.5})`;
      ctx.lineWidth = 1 + b.c;
      ctx.beginPath(); ctx.moveTo(from.x, from.y); ctx.lineTo(to.x, to.y); ctx.stroke();
      const angle = Math.atan2(to.y - from.y, to.x - from.x);
      const ax = to.x - 20 * Math.cos(angle), ay = to.y - 20 * Math.sin(angle);
      ctx.beginPath();
      ctx.moveTo(ax, ay);
      ctx.lineTo(ax - 7 * Math.cos(angle - 0.35), ay - 7 * Math.sin(angle - 0.35));
      ctx.lineTo(ax - 7 * Math.cos(angle + 0.35), ay - 7 * Math.sin(angle + 0.35));
      ctx.closePath(); ctx.fillStyle = ctx.strokeStyle; ctx.fill();
      const mx = (from.x + to.x) / 2, my = (from.y + to.y) / 2;
      ctx.font = "10px sans-serif"; ctx.fillStyle = "#888";
      ctx.fillText(`${b.f.toFixed(1)};${b.c.toFixed(2)}`, mx + 3, my - 3);
    });
    concepts.forEach((t) => {
      const p = pos[t];
      ctx.beginPath(); ctx.arc(p.x, p.y, 16, 0, 2 * Math.PI);
      ctx.fillStyle = "#fff"; ctx.fill();
      ctx.strokeStyle = "#333"; ctx.lineWidth = 1.5; ctx.stroke();
      ctx.font = "bold 11px sans-serif"; ctx.fillStyle = "#222";
      ctx.textAlign = "center"; ctx.textBaseline = "middle";
      ctx.fillText(t, p.x, p.y);
    });
  }, [beliefs, concepts]);
  return <canvas ref={ref} width={500} height={340} style={{ width: "100%", maxWidth: 500, height: "auto", border: "1px solid #ddd", borderRadius: 4 }} />;
}

/* ── Presets ───────────────────────────────────────────────────────── */

const PRESETS = [
  { name: "Deduction", cmds: ["<bird --> animal>. %1.0;0.9%", "<robin --> bird>. %1.0;0.9%", "10", "<robin --> animal>?"] },
  { name: "Abduction", cmds: ["<bird --> animal>. %1.0;0.9%", "<fish --> animal>. %1.0;0.9%", "10", "<fish --> bird>?"] },
  { name: "Induction", cmds: ["<water --> liquid>. %1.0;0.9%", "<water --> transparent>. %1.0;0.9%", "10", "<transparent --> liquid>?"] },
  { name: "Revision", cmds: ["<robin --> bird>. %0.7;0.9%", "<robin --> bird>. %0.9;0.8%", "5", "<robin --> bird>?"] },
  { name: "Negative Evidence", cmds: ["<penguin --> bird>. %1.0;0.9%", "<penguin --> flyer>. %0.0;0.9%", "10", "<bird --> flyer>?"] },
];

/* ── Main ─────────────────────────────────────────────────────────── */

export default function App() {
  const [engine] = useState(() => new NarsEngine());
  const [input, setInput] = useState("");
  const [log, setLog] = useState([]);
  const [beliefs, setBeliefs] = useState([]);
  const [concepts, setConcepts] = useState([]);
  const [view, setView] = useState("log");
  const endRef = useRef(null);

  const sync = useCallback(() => {
    setLog([...engine.log]);
    setBeliefs(engine.getBeliefs());
    setConcepts(engine.getConcepts());
  }, [engine]);

  useEffect(() => { endRef.current?.scrollIntoView({ behavior: "smooth" }); }, [log]);

  const run = () => {
    if (!input.trim()) return;
    const num = parseInt(input.trim());
    if (!isNaN(num) && num > 0) engine.runCycles(num);
    else { engine.input(input.trim()); engine.runCycles(3); }
    sync(); setInput("");
  };

  const runPreset = (p) => {
    engine.reset();
    for (const cmd of p.cmds) {
      const n = parseInt(cmd);
      if (!isNaN(n) && n > 0) engine.runCycles(n);
      else { engine.input(cmd); engine.runCycles(3); }
    }
    sync();
  };

  const reset = () => { engine.reset(); sync(); };

  const colors = { IN: "#333", OUT: "#555", ANS: "#006600", REV: "#663399", ERR: "#cc0000" };
  const labels = { IN: "INPUT", OUT: "DERIVED", ANS: "ANSWER", REV: "REVISED", ERR: "ERROR" };

  return (
    <div style={{ fontFamily: "sans-serif", maxWidth: 800, margin: "0 auto", padding: "20px 16px", color: "#222" }}>

      <h1 style={{ fontSize: 22, fontWeight: 700, margin: "0 0 4px" }}>Mini-NARS</h1>
      <p style={{ fontSize: 13, color: "#666", margin: "0 0 20px" }}>
        Non-Axiomatic Reasoning System &mdash; Interactive Demo<br/>
        <span style={{ fontSize: 11 }}>Matthew Setiadi &middot; Temple ID: 916396491 &middot; CIS 5590 AGI &middot; Spring 2026</span>
      </p>

      {/* Presets */}
      <div style={{ marginBottom: 16 }}>
        <span style={{ fontSize: 12, color: "#888", marginRight: 8 }}>Try an example:</span>
        {PRESETS.map((p) => (
          <button key={p.name} onClick={() => runPreset(p)} style={{
            background: "#f5f5f5", border: "1px solid #ddd", borderRadius: 3,
            padding: "4px 10px", margin: "0 4px 4px 0", fontSize: 12, cursor: "pointer",
          }}>{p.name}</button>
        ))}
        <button onClick={reset} style={{
          background: "#fff", border: "1px solid #ccc", borderRadius: 3,
          padding: "4px 10px", fontSize: 12, cursor: "pointer", color: "#999",
        }}>Reset</button>
      </div>

      {/* Input */}
      <div style={{ display: "flex", gap: 8, marginBottom: 16 }}>
        <input
          value={input} onChange={(e) => setInput(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && run()}
          placeholder='e.g.  <robin --> bird>. %1.0;0.9%  or  <robin --> animal>?  or  10'
          style={{
            flex: 1, padding: "8px 12px", border: "1px solid #ccc", borderRadius: 4,
            fontSize: 13, fontFamily: "monospace",
          }}
        />
        <button onClick={run} style={{
          background: "#333", color: "#fff", border: "none", borderRadius: 4,
          padding: "8px 20px", fontSize: 13, cursor: "pointer",
        }}>Run</button>
      </div>

      {/* Tabs */}
      <div style={{ display: "flex", gap: 0, borderBottom: "1px solid #ddd", marginBottom: 12 }}>
        {["log", "beliefs", "graph"].map((t) => (
          <button key={t} onClick={() => setView(t)} style={{
            padding: "6px 16px", border: "none", background: "none", fontSize: 13, cursor: "pointer",
            borderBottom: view === t ? "2px solid #333" : "2px solid transparent",
            color: view === t ? "#222" : "#999", fontWeight: view === t ? 600 : 400,
          }}>{t === "log" ? "Output Log" : t === "beliefs" ? "Beliefs" : "Concept Graph"}</button>
        ))}
      </div>

      {/* Content */}
      <div style={{ minHeight: 300 }}>
        {view === "log" && (
          <div style={{ maxHeight: 400, overflow: "auto", fontFamily: "monospace", fontSize: 12, lineHeight: 1.8 }}>
            {log.length === 0 && <p style={{ color: "#aaa", textAlign: "center", padding: 40 }}>Click an example above or type Narsese below to start.</p>}
            {log.map((e, i) => (
              <div key={i} style={{ display: "flex", gap: 8, padding: "1px 0" }}>
                <span style={{ color: "#bbb", minWidth: 28, textAlign: "right" }}>{e.cy}</span>
                <span style={{ color: colors[e.type], minWidth: 60, fontWeight: e.type === "ANS" ? 700 : 400, fontSize: 10, paddingTop: 2 }}>
                  {labels[e.type]}
                </span>
                <span style={{ color: e.type === "ANS" ? "#006600" : "#444", fontWeight: e.type === "ANS" ? 600 : 400 }}>
                  {e.text}
                </span>
              </div>
            ))}
            <div ref={endRef} />
          </div>
        )}

        {view === "beliefs" && (
          <div>
            {beliefs.length === 0 && <p style={{ color: "#aaa", textAlign: "center", padding: 40 }}>No beliefs yet.</p>}
            <table style={{ width: "100%", borderCollapse: "collapse", fontSize: 13 }}>
              <thead>
                <tr style={{ borderBottom: "1px solid #ddd", textAlign: "left" }}>
                  <th style={{ padding: "6px 8px", fontWeight: 600, color: "#666" }}>Statement</th>
                  <th style={{ padding: "6px 8px", fontWeight: 600, color: "#666", width: 70 }}>Freq</th>
                  <th style={{ padding: "6px 8px", fontWeight: 600, color: "#666", width: 70 }}>Conf</th>
                  <th style={{ padding: "6px 8px", fontWeight: 600, color: "#666", width: 70 }}>Expect</th>
                  <th style={{ padding: "6px 8px", fontWeight: 600, color: "#666", width: 120 }}>Strength</th>
                </tr>
              </thead>
              <tbody>
                {beliefs.map((b, i) => (
                  <tr key={i} style={{ borderBottom: "1px solid #f0f0f0" }}>
                    <td style={{ padding: "6px 8px", fontFamily: "monospace" }}>{b.sub} &rarr; {b.pred}</td>
                    <td style={{ padding: "6px 8px" }}>{b.f.toFixed(2)}</td>
                    <td style={{ padding: "6px 8px" }}>{b.c.toFixed(2)}</td>
                    <td style={{ padding: "6px 8px" }}>{exp_(b.f, b.c).toFixed(2)}</td>
                    <td style={{ padding: "6px 8px" }}>
                      <div style={{ background: "#eee", borderRadius: 2, height: 8, width: "100%" }}>
                        <div style={{
                          width: `${exp_(b.f, b.c) * 100}%`, height: "100%", borderRadius: 2,
                          background: b.f > 0.5 ? "#444" : "#c00",
                        }} />
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}

        {view === "graph" && <ConceptGraph beliefs={beliefs} concepts={concepts} />}
      </div>

      {/* Reference */}
      <div style={{ marginTop: 24, padding: 16, background: "#f9f9f9", border: "1px solid #eee", borderRadius: 4, fontSize: 12, color: "#666" }}>
        <strong style={{ color: "#333" }}>Narsese Input Format</strong>
        <div style={{ marginTop: 8, fontFamily: "monospace", lineHeight: 2 }}>
          <div><code>&lt;subject --&gt; predicate&gt;. %f;c%</code> &mdash; Judgment (statement with truth-value)</div>
          <div><code>&lt;subject --&gt; predicate&gt;?</code> &mdash; Question (query the system)</div>
          <div><code>&lt;? --&gt; predicate&gt;?</code> &mdash; What inherits predicate?</div>
          <div><code>10</code> &mdash; Run 10 inference cycles</div>
        </div>
        <div style={{ marginTop: 12 }}>
          <strong style={{ color: "#333" }}>Inference Rules: </strong>
          <strong>Deduction</strong> (M&rarr;P + S&rarr;M = S&rarr;P, strong) &middot;
          <strong> Abduction</strong> (P&rarr;M + S&rarr;M = S&rarr;P, weak) &middot;
          <strong> Induction</strong> (M&rarr;P + M&rarr;S = S&rarr;P, weak) &middot;
          <strong> Revision</strong> (merge evidence, confidence increases)
        </div>
      </div>
    </div>
  );
}
