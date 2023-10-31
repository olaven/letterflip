(ns letterflip.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]))

(def alphabet {:a :b
               :b :c
               :c :d
               :d :e
               :e :f
               :f :g
               :g :h
               :h :i
               :i :j
               :j :k
               :k :l
               :l :m
               :m :n
               :n :o
               :o :p
               :p :q
               :q :r
               :r :s
               :s :t
               :t :u
               :u :v
               :v :w
               :w :x
               :x :y
               :y :z
               :z :a})

(defn next-letter
  [letter]
  (->> letter
       keyword
       (get alphabet)
       name))

;; Excerpt from https://www.gutenberg.org/cache/epub/76/pg76.txt
(def text "You don’t know about me without you have read a book by the name of The
Adventures of Tom Sawyer; but that ain’t no matter. That book was made
by Mr. Mark Twain, and he told the truth, mainly. There was things
which he stretched, but mainly he told the truth. That is nothing. I
never seen anybody but lied one time or another, without it was Aunt
Polly, or the widow, or maybe Mary. Aunt Polly—Tom’s Aunt Polly, she
is—and Mary, and the Widow Douglas is all told about in that book,
which is mostly a true book, with some stretchers, as I said before.")

(defn Letter
  [initial-letter]
  (let [letter (r/atom initial-letter)
        counter (r/atom 0)
        bold (r/atom false)]
    (defn trigger-transition
      []
      (reset! counter 0)
      (js/setInterval #(if (> 5 @counter)
                         (do
                           (swap! letter next-letter)
                           (swap! counter inc)
                           (reset! bold true))
                         (do (reset! letter initial-letter)
                             (reset! bold false))) 500))
    (fn []
      [:span
       {:on-mouse-over trigger-transition
        :style {:width ".75em"
                :font-size "1.25em"
                :font-weight (when @bold "bold")
                :display "inline-block"
                :textAlign "center"}} @letter])))

(defn Text
  [text]
  [:span (for [letter (clojure.string/split text "")]
           [Letter letter])])

(defn Root
  []
  [:p
   [Text text]])

(defn main []
  (rdom/render Root (js/document.getElementById "root")))



