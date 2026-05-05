# Mini-NARS: A Minimal Implementation of the Non-Axiomatic Reasoning System

A complete Python implementation of Mini-NARS as specified in Chapter 4 of *Designing a Mind: The Implementations of NARS* (Pei Wang, 2026).

## What is NARS?

NARS (Non-Axiomatic Reasoning System) is a general-purpose reasoning system designed to work under the **Assumption of Insufficient Knowledge and Resources (AIKR)**. Unlike traditional AI systems, NARS:

- Has no axioms. All knowledge is revisable based on evidence.
- Operates in real time with finite resources.
- Handles novel, open-ended problems.
- Uses experience-grounded semantics where truth is measured by evidential support.

## Architecture

```
    +------------------+
    |     memory       |   (bag of concepts)
    |   +-----------+  |
    |   | concept_1 |  |
    |   | concept_2 |  |
    |   | ...       |  |
    +---+-----+-----+--+
              |
        +-----v-----+
        | inference  |   (local, forward, backward rules)
        +-----+------+
              |
        +-----v-----+
        |   buffer   |   (priority queue of tasks)
        +-----+------+
              |
        +-----v-----+
        |  input /   |
        |  output    |
        +-----------+
```

## Quick Start

```bash
# Interactive mode
python main.py

# Run built-in tests
python main.py --test

# Load experience file
python main.py examples/animal_taxonomy.nal
```

## Narsese Syntax

```
# Judgment: subject inherits predicate with truth-value <frequency;confidence>
<robin --> bird>. %1.0;0.9%

# Question: does this inheritance hold?
<robin --> animal>?

# Question with variable: what does robin inherit?
<robin --> ?>?

# Question with variable: what inherits bird?
<? --> bird>?
```

## Inference Rules

### Local Rules
- **Revision**: Merges evidence from two sources for the same statement
- **Choice**: Selects the best answer among candidates for a question

### Syllogistic Rules (Forward and Backward)
| Figure | Premises | Conclusion | Type |
|--------|----------|------------|------|
| 1 | M -> P, S -> M | S -> P | Deduction (strong) |
| 2 | P -> M, S -> M | S -> P | Abduction (weak) |
| 3 | M -> P, M -> S | S -> P | Induction (weak) |
| 4 | S -> M, M -> P | S -> P | Exemplification (weak) |

### Truth-Value Functions

Truth values use extended Boolean operators:
- `and(x,y) = x * y`
- `or(x,y) = 1 - (1-x)(1-y)`
- `not(x) = 1 - x`

**Deduction**: `f = f1*f2, c = f1*c1*f2*c2`

**Induction/Abduction**: Weak inference producing `c < 0.5` (when k=1)

**Revision**: Pools evidence additively, increasing confidence

## Interactive Commands

| Command | Description |
|---------|-------------|
| `<S --> P>. %f;c%` | Input judgment |
| `<S --> P>?` | Ask question |
| `123` | Run 123 inference cycles |
| `:memory` | Show all concepts |
| `:beliefs` | Show all beliefs |
| `:beliefs term` | Show beliefs for a specific term |
| `:reset` | Reset the system |
| `:test` | Run built-in tests |
| `:help` | Show help |
| `:quit` | Exit |

## Example Session

```
nars> <bird --> animal>. %1.0;0.9%
  [0] IN: <bird --> animal>. %1.00;0.90%

nars> <robin --> bird>. %1.0;0.9%
  [0] IN: <robin --> bird>. %1.00;0.90%

nars> 5

nars> <robin --> animal>?
  [5] IN: <robin --> animal>?

nars> 20
  [15] OUT: <robin --> animal>. %1.00;0.81%
```

The system correctly derives that robin is an animal through deductive inference with confidence 0.81 (= 0.9 * 0.9).

## Project Structure

```
mini_nars/
  main.py              # Entry point, CLI, tests
  mini_nars.py          # MiniNARS engine: inference, memory, control
  data_structures.py    # Statement, TruthValue, Task, Belief, Concept
  bag.py                # Bag: probabilistic priority queue
  truth_functions.py    # NAL truth-value functions
  budget_functions.py   # Resource allocation functions
  examples/
    animal_taxonomy.nal  # Sample experience file
```

## Key Design Decisions

1. **Bag as probabilistic priority queue**: Uses a distributor array for deterministic yet priority-weighted selection, matching the specification exactly.

2. **Evidence tracking**: Each truth-value carries an evidential base (serial numbers of input tasks) to prevent circular reasoning during revision.

3. **Concept-centered memory**: Knowledge is clustered by shared terms, enabling efficient syllogistic matching.

4. **Case-by-case processing**: No fixed algorithm per task. Each cycle selects one concept, one task, one belief, and performs one inference step.


## AI disclosure
 
The core Python files were driven entirely by my own ideas and logic, following Chapter 4 of the book directly. I used AI (Gemini) strictly as an aid to check for errors, debug issues, and format the code more cleanly.
 
The web demo (mini_nars_ui.jsx) was built primarily with AI, followed by manual tweaks on my end. It is not part of the assignment and is just an add-on for presentation. The exact prompt used to generate the website code is attached in the submission files.
 
The full AI conversation prompt link is attached for transparency.
 

## References

- Wang, P. (2026). *Designing a Mind: The Implementations of NARS*. Draft.
- Wang, P. (2025). *Non-Axiomatic Logic: A Model of Intelligent Reasoning*. 2nd ed. World Scientific.
- OpenNARS-4 / PyNARS: https://github.com/opennars/OpenNARS-4/tree/dev/pynars
- AI Conversation Prompt (Claude): https://claude.ai/share/db7b5191-d7b9-4be0-8732-ec824f69082c
