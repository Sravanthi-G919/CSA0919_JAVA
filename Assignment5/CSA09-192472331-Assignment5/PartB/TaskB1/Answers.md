# Task B1 – Run and record evidence
1. Run `PassStore`. Expected key output includes `[P1003, 192311003, Divya, 7, 3800.0]` and `null` for P1002. The malformed P1002 line has only two fields, so `find` skips it and continues.
2. Run `GenerateLineFiles` to create 1 MB, 10 MB and 50 MB test files, then run `BufferedLineCounter` on each file. Record your actual times in `PartB/results/buffering-measurements.csv`.
3. Run `BusPassGrowthBenchmark` and record actual results in `PartB/results/growth-measurements.csv`.
4. The pass store uses a line-by-line `BufferedReader`, try-with-resources and malformed-row checks. A scan is O(n); auxiliary memory is O(1).
