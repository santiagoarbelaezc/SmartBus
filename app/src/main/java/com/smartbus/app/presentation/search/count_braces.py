
import sys

def count_braces(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    balance = 0
    line_num = 1
    for char in content:
        if char == '{':
            balance += 1
        elif char == '}':
            balance -= 1
        
        if char == '\n':
            line_num += 1
        
        if balance < 0:
            print(f"Negative balance at line {line_num}")
            balance = 0 # reset for further scanning
    
    print(f"Final balance for {file_path}: {balance}")

if __name__ == "__main__":
    count_braces(sys.argv[1])
