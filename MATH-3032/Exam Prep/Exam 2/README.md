# MATH 3032 — Chapter 3 Point Estimation: Complete Exam Prep

A comprehensive, self-contained study guide covering all 8 exam question types for Chapter 3 of Mathematical Statistics, based on:

- **Panaretos**, *Statistics for Mathematicians* (Chapter 3)
- **Hogg, Tanis & Zimmerman**, *Probability and Statistical Inference*, 9th ed. (§6.4, §6.6)

Written by **Matthew Setiadi** — Accelerated B.S./M.S. Computational Data Science, Temple University.

---

## What's inside

| Section | Content |
|---------|---------|
| §1 | Statistical framework + formula sheet (gamma integrals) |
| §2 | All 8 exam questions with full theory, definitions, and worked examples |
| §3 | Five practice problems across different distributions |
| §4 | Consistency: LLN vs. Chebyshev comparison table |
| §5 | The three-way identity (why both CLT routes agree when CR bound is attained) |
| §6 | Full mock exam — Weibull(2, θ), Q1–Q8 with blank workspace + solutions |
| §7 | Quick reference cheat sheet + exam checklist |

## The 8 exam questions

1. **Likelihood function** `L(θ)` — write and simplify the joint pdf
2. **MLE** `θ̂` — score equation + second derivative test
3. **Bias** — compute `E[X₁]` via u-substitution and gamma integral table
4. **Mean Squared Error** — compute `E[X₁²]`, `Var(X₁)`, `Var(θ̂)`
5. **Consistency** — Law of Large Numbers and Chebyshev approaches
6. **Fisher information** `I(θ)` — score function and variance shortcut
7. **Cramér-Rao lower bound** — attained vs. not attained (Uniform counterexample)
8. **Asymptotic normality** — find σ via Classical CLT and MLE CLT theorem

## Distributions covered

| Distribution | Used for |
|-------------|----------|
| Gamma(3, θ) | Main running example — full Q1–Q8 chain |
| Rayleigh(θ) | MLE + second derivative practice |
| Weibull(2, θ) | Likelihood practice + full mock exam |
| Exponential(λ) | Fisher information example |
| Uniform(0, θ) | CR bound NOT attained — irregular model |

## Key results derived

```
Gamma(3, θ):   E[X₁] = 3θ,  Var(X₁) = 3θ²,  I(θ) = 3/θ²,  MSE = θ²/(3n),  σ = θ/√3
Rayleigh(θ):   θ̂ = √(ΣXᵢ²/n),  I(θ) = 4/θ²
Weibull(2,θ):  E[X₁²] = θ²,  Var(X₁²) = θ⁴,  I(θ) = 4/θ²,  σ = θ²
Exp(λ):        I(λ) = 1/λ²,  CRLB = λ²/n,  σ = λ
Uniform(0,θ):  Var(T*) = θ²/[n(n+2)] < θ²/n = formal CRLB  [bound invalid]
```

## The three-way identity

When the Cramér-Rao bound is attained:

```
Var(g(X₁)) = 1/I(θ) = n·Var(θ̂) = n·CRLB = σ²
```

This identity holds **if and only if** the MLE is the UMVUE. It also explains why the Classical CLT and the MLE CLT give the same σ for Q8.

## How to use

**Download the PDF** directly from this repo — no LaTeX installation needed.

**Compile from source** if you want to customise it:
```bash
pdflatex math3032_exam_prep.tex
pdflatex math3032_exam_prep.tex   # run twice for TOC
```

Requires a standard TeX Live or MiKTeX installation with `amsmath`, `tcolorbox`, `booktabs`, `hyperref`.

## License

Released under [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/) — free to use, share, adapt, and build on, with attribution.

---

*If this helped you, feel free to star the repo or share it with your class.*
