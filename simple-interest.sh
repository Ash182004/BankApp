#!/usr/bin/env bash

# Simple interest calculator

echo "Simple Interest Calculator"
read -r -p "Enter principal amount: " principal
read -r -p "Enter rate of interest (annual %): " rate
read -r -p "Enter time period (years): " time

interest=$(awk "BEGIN { printf \"%.2f\", ($principal * $rate * $time) / 100 }")

echo "Simple interest is: $interest"
