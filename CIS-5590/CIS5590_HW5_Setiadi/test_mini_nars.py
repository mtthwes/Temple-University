#!/usr/bin/env python3
"""
Automated test suite for Mini-NARS with assertions.
Tests all inference rules, data structures, and system behavior.
"""

import sys
import os
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

from truth_functions import (
    deduction, induction, abduction, exemplification,
    revision as tv_revision, _and, _or, _not, expectation,
    intersection, union, difference, negation, conversion
)
from data_structures import Statement, TruthValue, BudgetValue, Task, Belief, Concept
from bag import Bag
from mini_nars import MiniNARS


passed = 0
failed = 0


def assert_close(actual, expected, tol=0.02, msg=""):
    global passed, failed
    if abs(actual - expected) <= tol:
        passed += 1
    else:
        failed += 1
        print(f"  FAIL: {msg} expected={expected:.4f} actual={actual:.4f}")


def assert_true(cond, msg=""):
    global passed, failed
    if cond:
        passed += 1
    else:
        failed += 1
        print(f"  FAIL: {msg}")


def find_belief(nars, subject, predicate):
    """Find a belief in the system matching the given statement."""
    for concept in nars.memory.all_items():
        for belief in concept.belief_bag.all_items():
            if belief.statement.subject == subject and belief.statement.predicate == predicate:
                best = belief.best_truth()
                if best:
                    return best
    return None


# ===========================================================================
# 1. TRUTH-VALUE FUNCTIONS
# ===========================================================================

def test_extended_boolean():
    print("  Testing extended Boolean operators...")
    assert_close(_and(1.0, 1.0), 1.0, msg="and(1,1)")
    assert_close(_and(0.5, 0.5), 0.25, msg="and(0.5,0.5)")
    assert_close(_and(1.0, 0.0), 0.0, msg="and(1,0)")
    assert_close(_or(0.0, 0.0), 0.0, msg="or(0,0)")
    assert_close(_or(0.5, 0.5), 0.75, msg="or(0.5,0.5)")
    assert_close(_or(1.0, 0.0), 1.0, msg="or(1,0)")
    assert_close(_not(0.0), 1.0, msg="not(0)")
    assert_close(_not(0.7), 0.3, msg="not(0.7)")
    assert_close(_and(0.9, 0.8, 0.7), 0.504, msg="and(0.9,0.8,0.7)")


def test_deduction():
    print("  Testing deduction truth function...")
    # {M->P %1.0;0.9%, S->M %1.0;0.9%} |- S->P
    f, c = deduction(1.0, 0.9, 1.0, 0.9)
    assert_close(f, 1.0, msg="deduction f")
    assert_close(c, 0.81, msg="deduction c")

    # Partial truth
    f, c = deduction(0.8, 0.9, 0.7, 0.9)
    assert_close(f, 0.56, msg="deduction partial f")  # 0.8*0.7
    assert_close(c, 0.8*0.9*0.7*0.9, msg="deduction partial c")


def test_abduction():
    print("  Testing abduction truth function...")
    f, c = abduction(1.0, 0.9, 1.0, 0.9)
    # w+ = and(1,1,0.9,0.9)=0.81, w = and(1,0.9,0.9)=0.81
    # c = 0.81/1.81 ≈ 0.4475
    assert_true(c < 0.5, msg="abduction is weak (c < 0.5)")
    assert_close(f, 1.0, msg="abduction f")


def test_induction():
    print("  Testing induction truth function...")
    f, c = induction(1.0, 0.9, 1.0, 0.9)
    assert_true(c < 0.5, msg="induction is weak (c < 0.5)")
    assert_close(f, 1.0, msg="induction f")


def test_exemplification():
    print("  Testing exemplification truth function...")
    f, c = exemplification(1.0, 0.9, 1.0, 0.9)
    assert_true(c < 0.5, msg="exemplification is weak (c < 0.5)")


def test_revision():
    print("  Testing revision truth function...")
    # Two sources: %0.7;0.9% and %0.9;0.8%
    f, c = tv_revision(0.7, 0.9, 0.9, 0.8)
    # w1: c1=0.9, w1=0.9/0.1=9, wp1=9*0.7=6.3
    # w2: c2=0.8, w2=0.8/0.2=4, wp2=4*0.9=3.6
    # wp=9.9, w=13, f=9.9/13≈0.762, c=13/14≈0.929
    assert_close(f, 0.762, tol=0.01, msg="revision f")
    assert_close(c, 0.929, tol=0.01, msg="revision c")
    assert_true(c > 0.9, msg="revision increases confidence")


def test_negation():
    print("  Testing negation truth function...")
    f, c = negation(0.8, 0.9)
    assert_close(f, 0.2, msg="negation f")
    assert_close(c, 0.9, msg="negation c preserved")


def test_conversion():
    print("  Testing conversion truth function...")
    f, c = conversion(1.0, 0.9)
    assert_true(c < 0.5, msg="conversion is weak")


def test_composition():
    print("  Testing composition truth functions...")
    f, c = intersection(0.8, 0.9, 0.6, 0.9)
    assert_close(f, 0.48, msg="intersection f")  # 0.8*0.6
    assert_close(c, 0.81, msg="intersection c")   # 0.9*0.9

    f, c = union(0.8, 0.9, 0.6, 0.9)
    assert_close(f, _or(0.8, 0.6), msg="union f")

    f, c = difference(0.8, 0.9, 0.6, 0.9)
    assert_close(f, 0.8 * 0.4, msg="difference f")  # 0.8*(1-0.6)


def test_expectation():
    print("  Testing expectation function...")
    e = expectation(1.0, 0.9)
    assert_close(e, 0.95, msg="exp(1.0,0.9)")
    e = expectation(0.5, 0.5)
    assert_close(e, 0.5, msg="exp(0.5,0.5)")
    e = expectation(0.0, 0.9)
    assert_close(e, 0.05, msg="exp(0.0,0.9)")


# ===========================================================================
# 2. DATA STRUCTURES
# ===========================================================================

def test_statement():
    print("  Testing Statement...")
    s1 = Statement("robin", "bird")
    s2 = Statement("robin", "bird")
    s3 = Statement("bird", "animal")
    assert_true(s1 == s2, msg="equal statements")
    assert_true(s1 != s3, msg="unequal statements")
    assert_true(hash(s1) == hash(s2), msg="hash consistency")


def test_truth_value():
    print("  Testing TruthValue...")
    tv = TruthValue(0.8, 0.9, [1, 2])
    assert_close(tv.frequency, 0.8, msg="tv frequency")
    assert_close(tv.confidence, 0.9, msg="tv confidence")
    assert_true(tv.evidential_base == [1, 2], msg="tv evidential base")
    e = tv.expectation()
    assert_close(e, 0.77, msg="tv expectation")


def test_belief():
    print("  Testing Belief...")
    s = Statement("robin", "bird")
    tv1 = TruthValue(0.8, 0.7, [1])
    tv2 = TruthValue(0.9, 0.9, [2])
    b = Belief(s, tv1)
    b.add_truth(tv2)
    # Best truth should be highest confidence
    best = b.best_truth()
    assert_close(best.confidence, 0.9, msg="best truth is highest conf")

    # Select truth with disjoint evidence
    task = Task(s, TruthValue(0.5, 0.5, [1]))
    selected = b.select_truth(task)
    assert_true(selected is not None, msg="select_truth finds disjoint")
    assert_close(selected.confidence, 0.9, msg="selected is tv2 (disjoint from [1])")


# ===========================================================================
# 3. BAG DATA STRUCTURE
# ===========================================================================

def test_bag_basic():
    print("  Testing Bag basic operations...")
    bag = Bag(levels=10, bucket_capacity=3)

    class TestItem:
        def __init__(self, k, p):
            self.key = k
            self.priority = p
            self.durability = 0.9
        def merge(self, other):
            self.priority = _or(self.priority, other.priority)

    bag.put(TestItem("a", 0.5))
    bag.put(TestItem("b", 0.8))
    bag.put(TestItem("c", 0.2))
    assert_true(len(bag) == 3, msg="bag size")
    assert_true("a" in bag, msg="bag contains")

    item = bag.get("b")
    assert_true(item is not None, msg="bag get")
    assert_true(item.key == "b", msg="bag get key")
    assert_true(len(bag) == 2, msg="bag size after get")


def test_bag_selection_bias():
    print("  Testing Bag selection bias (high priority selected more)...")

    class TestItem:
        def __init__(self, k, p):
            self.key = k
            self.priority = p
            self.durability = 0.99
        def merge(self, other):
            self.priority = max(self.priority, other.priority)

    bag = Bag(levels=10, bucket_capacity=5)
    bag.put(TestItem("high", 0.95))
    bag.put(TestItem("low", 0.05))

    counts = {"high": 0, "low": 0}
    for _ in range(200):
        item = bag.select()
        if item:
            counts[item.key] += 1
            bag.put_back(item)

    assert_true(counts["high"] > counts["low"],
                msg=f"high({counts['high']}) > low({counts['low']})")


def test_bag_overflow():
    print("  Testing Bag overflow (forgetting)...")

    class TestItem:
        def __init__(self, k, p):
            self.key = k
            self.priority = p
            self.durability = 0.9
        def merge(self, other):
            self.priority = _or(self.priority, other.priority)

    bag = Bag(levels=3, bucket_capacity=2)  # capacity = 6
    for i in range(10):
        bag.put(TestItem(f"item_{i}", 0.1 * (i + 1)))

    assert_true(len(bag) <= 6, msg=f"bag overflow handled (size={len(bag)})")


# ===========================================================================
# 4. INFERENCE RULES (SYSTEM LEVEL)
# ===========================================================================

def test_system_deduction():
    print("  Testing system-level deduction...")
    nars = MiniNARS(silent=True)
    nars.input_narsese("<bird --> animal>. %1.0;0.9%")
    nars.input_narsese("<robin --> bird>. %1.0;0.9%")
    nars.cycle(30)

    tv = find_belief(nars, "robin", "animal")
    assert_true(tv is not None, msg="robin -> animal derived")
    if tv:
        assert_close(tv.frequency, 1.0, msg="deduction f=1.0")
        assert_close(tv.confidence, 0.81, msg="deduction c=0.81")


def test_system_abduction():
    print("  Testing system-level abduction...")
    nars = MiniNARS(silent=True)
    nars.input_narsese("<bird --> animal>. %1.0;0.9%")
    nars.input_narsese("<fish --> animal>. %1.0;0.9%")
    nars.cycle(30)

    tv = find_belief(nars, "fish", "bird")
    assert_true(tv is not None, msg="fish -> bird derived (abduction)")
    if tv:
        assert_true(tv.confidence < 0.5, msg="abduction is weak")


def test_system_induction():
    print("  Testing system-level induction...")
    nars = MiniNARS(silent=True)
    nars.input_narsese("<water --> liquid>. %1.0;0.9%")
    nars.input_narsese("<water --> transparent>. %1.0;0.9%")
    nars.cycle(30)

    tv = find_belief(nars, "transparent", "liquid")
    assert_true(tv is not None, msg="transparent -> liquid derived (induction)")
    if tv:
        assert_true(tv.confidence < 0.5, msg="induction is weak")


def test_system_revision():
    print("  Testing system-level revision...")
    nars = MiniNARS(silent=True)
    nars.input_narsese("<robin --> bird>. %0.7;0.9%")
    nars.input_narsese("<robin --> bird>. %0.9;0.8%")
    nars.cycle(20)

    tv = find_belief(nars, "robin", "bird")
    assert_true(tv is not None, msg="revised belief exists")
    if tv:
        assert_true(tv.confidence > 0.9, msg="revision increased confidence")


def test_system_negative_evidence():
    print("  Testing negative evidence handling...")
    nars = MiniNARS(silent=True)
    nars.input_narsese("<penguin --> bird>. %1.0;0.9%")
    nars.input_narsese("<penguin --> flyer>. %0.0;0.9%")
    nars.cycle(30)

    tv = find_belief(nars, "bird", "flyer")
    assert_true(tv is not None, msg="bird -> flyer derived")
    if tv:
        assert_close(tv.frequency, 0.0, msg="negative freq preserved")
        assert_true(tv.confidence < 0.5, msg="weak inference")


def test_system_chained_deduction():
    print("  Testing chained deduction (3-step)...")
    nars = MiniNARS(silent=True)
    nars.input_narsese("<robin --> bird>. %1.0;0.9%")
    nars.input_narsese("<bird --> animal>. %1.0;0.9%")
    nars.input_narsese("<animal --> living>. %1.0;0.9%")
    nars.cycle(200)

    # Direct deduction (1 hop)
    tv2 = find_belief(nars, "bird", "living")
    assert_true(tv2 is not None, msg="bird -> living derived (1-hop deduction)")
    if tv2:
        assert_close(tv2.confidence, 0.81, msg="bird -> living c=0.81")

    # Multi-hop: robin -> living (may arrive through various paths)
    tv3 = find_belief(nars, "robin", "living")
    assert_true(tv3 is not None, msg="robin -> living derived (multi-hop)")

    # Also test simple 2-step deduction in isolation
    nars2 = MiniNARS(silent=True)
    nars2.input_narsese("<robin --> bird>. %1.0;0.9%")
    nars2.input_narsese("<bird --> animal>. %1.0;0.9%")
    nars2.cycle(30)
    tv_simple = find_belief(nars2, "robin", "animal")
    assert_true(tv_simple is not None, msg="robin -> animal (2-step deduction)")
    if tv_simple:
        assert_close(tv_simple.confidence, 0.81, msg="robin -> animal c=0.81")


def test_system_question_answering():
    print("  Testing question answering...")
    nars = MiniNARS(silent=True)
    nars.input_narsese("<bird --> animal>. %1.0;0.9%")
    nars.input_narsese("<robin --> bird>. %1.0;0.9%")
    nars.cycle(20)

    task = nars.input_narsese("<robin --> animal>?")
    nars.cycle(30)

    # Check output log for answer
    has_answer = any("robin --> animal" in msg and "ANS" in msg
                     for msg in nars.output_log)
    # Also check via belief
    tv = find_belief(nars, "robin", "animal")
    assert_true(tv is not None, msg="answer exists as belief")


def test_system_variable_query():
    print("  Testing variable queries...")
    nars = MiniNARS(silent=True)
    nars.input_narsese("<robin --> bird>. %1.0;0.9%")
    nars.input_narsese("<eagle --> bird>. %1.0;0.9%")
    nars.cycle(10)

    # "What inherits bird?"
    nars.input_narsese("<? --> bird>?")
    nars.cycle(20)

    has_answer = any("bird" in msg and "ANS" in msg for msg in nars.output_log)
    assert_true(has_answer, msg="variable query answered")


# ===========================================================================
# 5. PARSER
# ===========================================================================

def test_parser():
    print("  Testing Narsese parser...")
    from mini_nars import NarseseParser
    p = NarseseParser()

    # Judgment with truth-value
    t = p.parse("<robin --> bird>. %0.8;0.9%", 1)
    assert_true(t is not None, msg="parse judgment")
    assert_true(not t.is_question, msg="is judgment")
    assert_close(t.truth_value.frequency, 0.8, msg="parsed freq")
    assert_close(t.truth_value.confidence, 0.9, msg="parsed conf")
    assert_true(t.statement.subject == "robin", msg="parsed subject")
    assert_true(t.statement.predicate == "bird", msg="parsed predicate")

    # Question
    t = p.parse("<robin --> animal>?", 2)
    assert_true(t is not None, msg="parse question")
    assert_true(t.is_question, msg="is question")

    # Variable question
    t = p.parse("<? --> bird>?", 3)
    assert_true(t is not None, msg="parse variable question")
    assert_true(t.statement.subject == "?", msg="variable subject")

    # Default truth-value
    t = p.parse("<robin --> bird>.", 4)
    assert_true(t is not None, msg="parse with defaults")
    assert_close(t.truth_value.frequency, 1.0, msg="default freq")
    assert_close(t.truth_value.confidence, 0.9, msg="default conf")

    # With budget
    t = p.parse("<robin --> bird>. %0.7;0.8% $0.5;0.6$", 5)
    assert_true(t is not None, msg="parse with budget")
    assert_close(t.budget.priority, 0.5, msg="parsed priority")
    assert_close(t.budget.durability, 0.6, msg="parsed durability")


# ===========================================================================
# 6. MEDICAL DOMAIN (CIS 5590 relevance)
# ===========================================================================

def test_medical_domain():
    print("  Testing medical diagnosis domain...")
    nars = MiniNARS(silent=True)

    # Knowledge base
    nars.input_narsese("<flu --> disease>. %1.0;0.9%")
    nars.input_narsese("<cold --> disease>. %1.0;0.9%")
    nars.input_narsese("<fever --> flu_symptom>. %1.0;0.9%")
    nars.input_narsese("<cough --> cold_symptom>. %1.0;0.9%")
    nars.input_narsese("<headache --> flu_symptom>. %1.0;0.9%")
    nars.cycle(50)

    # System should find connections between flu and cold via 'disease'
    tv = find_belief(nars, "flu", "cold")
    if tv:
        assert_true(tv.confidence < 0.5, msg="flu->cold is weak (abduction)")

    tv2 = find_belief(nars, "cold", "flu")
    if tv2:
        assert_true(tv2.confidence < 0.5, msg="cold->flu is weak (abduction)")

    # fever and headache share flu_symptom
    tv3 = find_belief(nars, "fever", "headache")
    if tv3:
        assert_true(tv3.confidence < 0.5, msg="fever->headache is weak")


# ===========================================================================
# RUN ALL TESTS
# ===========================================================================

def run_all():
    global passed, failed
    
    print("\n" + "=" * 60)
    print("  MINI-NARS AUTOMATED TEST SUITE")
    print( "=" * 60)

    print("\n[1] Truth-Value Functions")
    test_extended_boolean()
    test_deduction()
    test_abduction()
    test_induction()
    test_exemplification()
    test_revision()
    test_negation()
    test_conversion()
    test_composition()
    test_expectation()

    print("\n[2] Data Structures")
    test_statement()
    test_truth_value()
    test_belief()

    print("\n[3] Bag Data Structure")
    test_bag_basic()
    test_bag_selection_bias()
    test_bag_overflow()

    print("\n[4] System-Level Inference")
    test_system_deduction()
    test_system_abduction()
    test_system_induction()
    test_system_revision()
    test_system_negative_evidence()
    test_system_chained_deduction()
    test_system_question_answering()
    test_system_variable_query()

    print("\n[5] Parser")
    test_parser()

    print("\n[6] Medical Domain")
    test_medical_domain()

    print("\n" + "=" * 60)
    total = passed + failed
    if failed == 0:
        print(f"  ALL {total} TESTS PASSED")
    else:
        print(f"  {passed}/{total} passed, {failed} FAILED")
    print("=" * 60 + "\n")

    return failed == 0


if __name__ == "__main__":
    success = run_all()
    sys.exit(0 if success else 1)
