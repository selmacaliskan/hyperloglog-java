# HyperLogLog Cardinality Estimation Algorithm (Java Implementation)

## Project Description

This project implements the **HyperLogLog algorithm in Java**.  
HyperLogLog is a **probabilistic data structure** used to estimate the number of distinct elements (cardinality) in very large datasets.

Instead of storing all elements, HyperLogLog stores only small statistical information, allowing it to estimate the number of unique values using **very little memory**.

This algorithm is widely used in **Big Data systems and distributed databases**.

---

# Algorithm Overview

The HyperLogLog algorithm works in several steps:

1. Each incoming element is passed through a **hash function**.
2. The hash value is used to determine a **bucket (register)**.
3. The algorithm counts the number of **leading zeros** in the remaining hash bits.
4. The register is updated if the new value is larger.
5. A **harmonic mean calculation** is used to estimate the total number of distinct elements.

---

# Algorithm Formula

The cardinality estimation formula used by HyperLogLog:

E = αₘ * m² * ( Σ 2^-M[j] )⁻¹

Where:

- **m** → number of registers
- **M[j]** → value stored in register j
- **αₘ** → bias correction constant

---

# Algorithm Complexity

## Time Complexity

| Operation | Complexity |
|----------|-----------|
| Add element | O(1) |
| Estimate cardinality | O(m) |
| Merge two structures | O(m) |

## Space Complexity

O(m)

Where **m = 2ᵖ** represents the number of registers.

---

# Error Rate

HyperLogLog provides approximate results with a known error bound.

Relative Error ≈ 1.04 / √m

Increasing the number of registers **reduces the estimation error**.

---

# Implementation Details

The Java implementation includes the following main functions:

- `hash()` → Generates a hash value using SHA-256  
- `leadingZeros()` → Counts leading zero bits in the hash value  
- `add()` → Adds a new element to the structure  
- `estimate()` → Estimates the number of unique elements  
- `merge()` → Merges two HyperLogLog structures  

---

# Example Usage

```java
HyperLogLog hll = new HyperLogLog(10);

for (int i = 0; i < 100000; i++) {
    hll.add("user" + i);
}

System.out.println("Estimated cardinality: " + hll.estimate());
