use std::fs::File;
use std::io::{self, BufRead};
use std::path::Path;

enum MatchResult {
    Win,
    Lose,
    Draw,
}

enum RPS {
    Rock,
    Scissors,
    Paper,
}

fn main() {
    part_one();
    part_two();
}

fn part_one() {
    let mut score: i32 = 0;
    if let Ok(lines) = read_lines("input.txt") {
        for line in lines.flatten() {
            let split: Vec<&str> = line.split(' ').collect();
            let opponent: RPS = match split[0] {
                "A" => RPS::Rock,
                "B" => RPS::Paper,
                "C" => RPS::Scissors,
                &_ => panic!("Invalid opponent choice"),
            };
            let player: RPS = match split[1] {
                "X" => RPS::Rock,
                "Y" => RPS::Paper,
                "Z" => RPS::Scissors,
                &_ => panic!("Illegal player choice"),
            };
            score += calculate_points(player, opponent);
        }
    }
    println!("{score}");
}

fn part_two() {
    let mut score: i32 = 0;
    if let Ok(lines) = read_lines("input.txt") {
        for line in lines.flatten() {
            let split: Vec<&str> = line.split(' ').collect();
            let opponent: RPS = match split[0] {
                "A" => RPS::Rock,
                "B" => RPS::Paper,
                "C" => RPS::Scissors,
                &_ => panic!("Invalid opponent choice"),
            };
            let match_result: MatchResult = match split[1] {
                "X" => MatchResult::Lose,
                "Y" => MatchResult::Draw,
                "Z" => MatchResult::Win,
                &_ => panic!("Invalid match result"),
            };
            score += calculate_points(calculate_player(&opponent, match_result), opponent);
        }
    }
    println!("{score}");
}

fn calculate_player(opponent: &RPS, result: MatchResult) -> RPS {
    match opponent {
        RPS::Rock => match result {
            MatchResult::Draw => RPS::Rock,
            MatchResult::Win => RPS::Paper,
            MatchResult::Lose => RPS::Scissors,
        },
        RPS::Scissors => match result {
            MatchResult::Draw => RPS::Scissors,
            MatchResult::Win => RPS::Rock,
            MatchResult::Lose => RPS::Paper,
        },
        RPS::Paper => match result {
            MatchResult::Draw => RPS::Paper,
            MatchResult::Win => RPS::Scissors,
            MatchResult::Lose => RPS::Rock,
        }
    }
}

fn read_lines<P>(filename: P) -> io::Result<io::Lines<io::BufReader<File>>>
    where P: AsRef<Path>, {
    let file = File::open(filename)?;
    Ok(io::BufReader::new(file).lines())
}

fn calculate_points(player: RPS, opponent: RPS) -> i32 {
    let choice_points = match player {
        RPS::Rock => 1,
        RPS::Scissors => 3,
        RPS::Paper => 2,
    };
    choice_points + match_outcome(determine_winner(player, opponent))
}

fn determine_winner(player: RPS, opponent: RPS) -> MatchResult {
    match player {
        RPS::Rock => match opponent {
            RPS::Rock => MatchResult::Draw,
            RPS::Paper => MatchResult::Lose,
            RPS::Scissors => MatchResult::Win,
        },
        RPS::Paper => match opponent {
            RPS::Rock => MatchResult::Win,
            RPS::Paper => MatchResult::Draw,
            RPS::Scissors => MatchResult::Lose,
        },
        RPS::Scissors => match opponent {
            RPS::Rock => MatchResult::Lose,
            RPS::Paper => MatchResult::Win,
            RPS::Scissors => MatchResult::Draw,
        }
    }
}

fn match_outcome(result: MatchResult) -> i32 {
    match result {
        MatchResult::Win => 6,
        MatchResult::Lose => 0,
        MatchResult::Draw => 3,
    }
}
