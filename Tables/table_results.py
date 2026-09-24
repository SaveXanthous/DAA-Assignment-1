"""Create a Markdown table from benchmark_data/results.csv.

Usage from the project root:
    python3 Tables/Codes/table_results.py

Writes Results/results_table.md.
"""

import csv
from pathlib import Path


PROJECT_DIR = Path(__file__).resolve().parents[1]
RESULTS_DIR = PROJECT_DIR / "Results"
CSV_FILE = RESULTS_DIR / "results.csv"
TABLE_FILE = RESULTS_DIR / "results_table.md"
FIELDS = ("algorithm", "input", "n", "time_ms", "comparisons", "max_depth")


def main():
    if not CSV_FILE.exists():
        raise SystemExit(f"CSV file not found: {CSV_FILE}")

    with CSV_FILE.open(newline="", encoding="utf-8") as csv_file:
        reader = csv.DictReader(csv_file)
        if not set(FIELDS).issubset(reader.fieldnames or ()):
            raise SystemExit("CSV does not have the expected benchmark columns")
        rows = list(reader)

    if not rows:
        raise SystemExit("CSV contains no benchmark results")

    headings = ("Algorithm", "Input", "n", "Time (ms)", "Comparisons", "Max depth")
    lines = [
        "| " + " | ".join(headings) + " |",
        "| " + " | ".join(("---", "---", "---:", "---:", "---:", "---:")) + " |",
    ]

    for row in rows:
        values = [row[field] for field in FIELDS]
        lines.append("| " + " | ".join(values) + " |")

    TABLE_FILE.write_text("\n".join(lines) + "\n", encoding="utf-8")
    print(f"Created {TABLE_FILE}")


if __name__ == "__main__":
    main()
