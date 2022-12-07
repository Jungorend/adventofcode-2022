(ns seven
  (:require [clojure.string :as s]))

(def filesystem (atom {"/" {:type :directory}}))
(def current-directory [])

(defn create-directory
  [name fs path]
  (assoc-in fs (conj path name) {:type :directory}))

(defn create-file
  [name size fs path]
  (assoc-in fs (conj path name) {:type :file
                                 :size size}))

(defn go-up-directory
  [cwd]
  (if (= 1 (count cwd))
    cwd ; Cannot go below "/"
    (apply vector (butlast cwd))))

(defn parse-commands
  [str fs cwd]
  (loop [commands (s/split str #"\n")
         cwd cwd
         fs fs]
    (if (empty? commands)
      fs
      (let [command (s/split (first commands) #" ")]
        (cond (= "$" (first command))
              (if (= "ls" (second command))
                (recur (rest commands) cwd fs)
                (if (= ".." (nth command 2))
                  (recur (rest commands) (go-up-directory cwd) fs)
                  (recur (rest commands) (conj cwd (nth command 2)) fs)))
              (= "dir" (first command))
              (recur (rest commands) cwd (create-directory (second command) fs cwd))
              :else
              (recur (rest commands) cwd (create-file (second command) (Long/parseLong (first command)) fs cwd)))))))

(def test
  "$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k")

(swap! filesystem #(parse-commands test % current-directory))
