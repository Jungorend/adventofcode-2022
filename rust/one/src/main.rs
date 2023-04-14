use std::fs::File;
use std::io::{self, BufRead};
use std::path::Path;

fn main() {
    let mut elves: Vec<i32> = Vec::new();
    elves.push(0);
    let mut current_elf = 0;
    if let Ok(lines) = read_lines("input.txt") {
        for line in lines.flatten() {
                if line.is_empty() {
                    elves.push(0);
                    current_elf += 1;
                } else {
                   elves[current_elf] += line.parse::<i32>().unwrap();
                }
        }
    }
    part_one(&elves);
    part_two(&mut elves);
}

fn read_lines<P>(filename: P) -> io::Result<io::Lines<io::BufReader<File>>>
    where P: AsRef<Path>, {
    let file = File::open(filename)?;
    Ok(io::BufReader::new(file).lines())
}

fn part_one(elves: &Vec<i32>) {
    let max_value = elves.iter().max();
    match max_value {
        Some(value) => println!("{}", value),
        None => println!("Empty"),
    }
}

fn part_two(elves: &mut Vec<i32>) {
    elves.sort_unstable_by(|x, y| y.cmp(x));
    let result: i32 = elves[0] + elves[1] + elves[2];
    println!("{result}");
}
