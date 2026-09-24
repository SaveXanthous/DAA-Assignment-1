"""Create benchmark plots from results.csv.

Usage from the project root:
    python3 Tables/Codes/plot_results.py

Creates PNG files in Results/plots/. Requires matplotlib:
    python3 -m pip install matplotlib
"""

import csv
import math
from pathlib import Path

try:
    import matplotlib.pyplot as plt
except ImportError as error:
    raise SystemExit(
        "Missing dependency matplotlib. Install it with: "
        "python3 -m pip install matplotlib"
    ) from error


PROJECT_DIR = Path(__file__).resolve().parents[1]
RESULTS_DIR = PROJECT_DIR / "Results"
CSV_FILE = RESULTS_DIR / "results.csv"
OUTPUT_DIR = RESULTS_DIR / "plots"
INPUT_TYPES = ("random", "sorted", "duplicates")
ALGORITHMS = ("MergeSort", "QuickSort", "QuickSelect")
COLORS = {
    "MergeSort": "#2563eb",
    "QuickSort": "#dc2626",
    "QuickSelect": "#16a34a",
}


def read_results():
    if not CSV_FILE.exists():
        raise SystemExit(f"CSV file not found: {CSV_FILE}")

    with CSV_FILE.open(newline="", encoding="utf-8") as csv_file:
        reader = csv.DictReader(csv_file)
        required = {"algorithm", "input", "n", "time_ms", "comparisons", "max_depth"}
        if not required.issubset(reader.fieldnames or ()):
            raise SystemExit("CSV must contain: " + ", ".join(sorted(required)))

        rows = []
        for row in reader:
            try:
                rows.append({
                    "algorithm": row["algorithm"],
                    "input": row["input"],
                    "n": int(row["n"]),
                    "time_ms": float(row["time_ms"]),
                    "comparisons": int(row["comparisons"]),
                    "max_depth": int(row["max_depth"]),
                })
            except (TypeError, ValueError) as error:
                raise SystemExit(f"Invalid CSV row: {row}") from error

    if not rows:
        raise SystemExit("CSV contains no benchmark results")
    return rows


def plot_metric(rows, input_type, field, title, ylabel, filename, ratio=False):
    figure, axis = plt.subplots(figsize=(8, 5))

    for algorithm in ALGORITHMS:
        points = sorted(
            (row["n"], row[field])
            for row in rows
            if row["input"] == input_type and row["algorithm"] == algorithm
        )
        if not points:
            continue

        sizes, values = zip(*points)
        if ratio:
            values = tuple(
                value / (n if algorithm == "QuickSelect" else n * math.log2(n))
                for n, value in points
            )

        axis.plot(
            sizes,
            values,
            marker="o",
            linewidth=2,
            label=algorithm,
            color=COLORS[algorithm],
        )

    axis.set_title(f"{title} — {input_type}")
    axis.set_xlabel("Array size (n)")
    axis.set_ylabel(ylabel)
    axis.set_xscale("log")
    axis.grid(True, which="both", linestyle="--", alpha=0.35)
    axis.legend()
    figure.tight_layout()
    figure.savefig(OUTPUT_DIR / filename, dpi=160)
    plt.close(figure)


def main():
    rows = read_results()
    OUTPUT_DIR.mkdir(exist_ok=True)

    for input_type in INPUT_TYPES:
        suffix = input_type
        plot_metric(
            rows, input_type, "time_ms", "Execution time", "Time (ms)",
            f"time_{suffix}.png",
        )
        plot_metric(
            rows, input_type, "max_depth", "Maximum recursion depth", "Depth",
            f"depth_{suffix}.png",
        )
        plot_metric(
            rows, input_type, "comparisons", "Normalized comparisons", "Comparisons / expected growth",
            f"ratio_{suffix}.png", ratio=True,
        )

    print(f"Created 9 plots in {OUTPUT_DIR}")


if __name__ == "__main__":
    main()
