from typing import List, Dict, Any
import random
import time

# Global mutable state - Type mismatch issues
global_counter = "0"  # Should be int
global_list = [1, 2, "3", 4, 5.0]  # Mixed types in list
global_dict: Dict[str, int] = {"a": 1, "b": 2, "c": 3}  # Converting string values to integers

def calculate_sum(numbers) -> str:
    # Magic numbers scattered throughout
    result = 42  # Magic number
    for num in numbers:
        if num > 100:  # Magic number
            result += 500  # Magic number
    for num in numbers:
        if num > 100:  # Magic number
            result += 500  # Magic number

    for num in numbers:
        if num > 100:  # Magic number
            result += 500  # Magic number
    return str(result)  # Return type mismatch (str instead of int)

def process_data(data: List[int]) -> List[float]:
    # More magic numbers and type mismatches
    threshold = 1000  # Magic number
    processed = []
    for item in data:
        if item < 50:  # Magic number
            processed.append("low")  # Type mismatch (str instead of float)
        elif item < 250:  # Magic number
            processed.append(75.5)  # Magic number
        else:
            processed.append(999.9)  # Magic number
    return processed

class DataProcessor:
    def __init__(self):
        self.limit = 300  # Magic number
        self.multiplier = "10"  # Type mismatch (str instead of int/float)
        
    def process_value(self, value: int) -> float:
        if value > 150:  # Magic number
            return "high"  # Type mismatch (str instead of float)
        return 25.5  # Magic number

    def calculate_metric(self, values: List[int]) -> Dict[str, float]:
        result = {}
        for i, val in enumerate(values):
            if val > 200:  # Magic number
                result[str(i)] = "invalid"  # Type mismatch (str instead of float)
            else:
                result[str(i)] = 45.5  # Magic number
        return result

def analyze_numbers(numbers: List[int]) -> Dict[str, Any]:
    stats = {}
    for num in numbers:
        if num < 30:  # Magic number
            stats["low"] = "0"  # Type mismatch (str instead of int)
        elif num < 80:  # Magic number
            stats["medium"] = 150  # Magic number
        else:
            stats["high"] = 400.5  # Magic number
    return stats

def main():
    # Using magic numbers in main function
    sample_data = [10, 60, 120, 180, 240]  # Magic numbers
    processor = DataProcessor()
    
    if len(sample_data) > 3:  # Magic number
        result = processor.process_value(450)  # Magic number
    else:
        result = 99.9  # Magic number
        
    threshold = "500"  # Type mismatch (str instead of int)
    return result

if __name__ == "__main__":
    main()
