Code Kata 19: Word Chains
=========================

The objective of this kata is to find word chains. A word chain
changes one word to another by changing one letter at a time, with
each intermediate result being a valid word.

For example, a word chain from "cat" to "dog" looks like this:
cat -> cot -> dot -> dog

The solution I implemented builds a graph of all the interconnected
words, starting with the start word. Then, it uses a breadth-first 
search to find the shortest path between the start word and 
end word.

This one was a real challenge for me. I haven't done anything with
graphs since college!
