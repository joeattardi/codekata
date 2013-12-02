Code Kata 19: Word Chains
=========================

The objective of this kata is to find word chains. A word chain
changes one word to another by changing one letter at a time, with
each intermediate result being a valid word.

For example, a word chain from "cat" to "dog" looks like this:
cat -> cot -> dot -> dog

The solution I implemented does a breadth-first search of the graph
of words. The graph is built on-the-fly as the breadth-first search
runs, rather than creating the full graph ahead of time. This saves
on memory used by the word graph.

This one was a real challenge for me. I haven't done anything with
graphs since college!
