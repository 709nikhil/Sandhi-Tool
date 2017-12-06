#!/bin/sh
while read -r word1 word2 _; do
  java Main $word1 $word2
done