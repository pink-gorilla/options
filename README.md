# options [![GitHub Actions status |pink-gorilla/options](https://github.com/pink-gorilla/options/workflows/CI/badge.svg)](https://github.com/pink-gorilla/options/actions?workflow=CI)[![Clojars Project](https://img.shields.io/clojars/v/org.pinkgorilla/options.svg)](https://clojars.org/org.pinkgorilla/options)


## features
- many functions can need options that can be specified by the user
- the idea is to define a "spec" which allows the ui to be generated automatically. 
- options is a map.
- available specs are:
  - :bool 
  - :string 
  - :select (from a list, possibly with a name that is different to the selected value) 

# example

```
 
(def state (r/atom {}))

(def config
  {:state state
   :current {; select
             :year 2023
             :client 2
             :pet :hamster
             ; bool
             :run-parallel true
             :debug? false
             ; string
             :search ""
             ; view
             :msg "hello!"}
   :options [{:path :year
              :name "Year"
              :spec (range 2018 2024)}
             {:path :client
              :name "Client"
              :spec [{:id 1 :name "Batman"}
                     {:id 2 :name "Robin"}
                     {:id 3 :name "Harry Potter"}
                     {:id 4 :name "Dumbledor"}
                     {:id 5 :name "The Hulk"}]
              :class "placeholder-gray-400 text-gray-700 relative bg-white rounded text-sm border border-gray-400 outline-none focus:outline-none focus:shadow-outline"}
             {:path :pet
              :name "Pet"
              :spec [:cat :dog :parrot :hamster]
              :class "placeholder-gray-400 text-gray-700 relative bg-white rounded text-sm border border-gray-400 outline-none focus:outline-none focus:shadow-outline"}

             {:path :run-parallel
              :name "RunParallel?"
              :spec :bool
              :class "pt-0 px-2 py-1 placeholder-gray-400 text-gray-700 relative bg-white rounded text-sm border border-gray-400 outline-none focus:outline-none focus:shadow-outline"}
             {:path :debug?
              :name "DebugMode"
              :spec :bool}
             {:path :search
              :name "SearchBox"
              :spec :string
              :class "px-2 py-1 placeholder-gray-400 text-gray-700 relative bg-white bg-white rounded text-sm border border-gray-400 outline-none focus:outline-none focus:shadow-outline"}
             {:name "Go!"
              :spec :button
              :class "bg-blue-500 hover:bg-blue-700 text-white font-bold rounded" ; py-2 px-4
              :on-click #(js/alert "yeah!")}
             
             {:name "view"
              :spec :view
              :path :msg
              }
             {:name "bad"
              :spec :view2}
             ]})

 [options-ui {:class "bg-blue-300 options-debug options-label-left"
              :style {:width "80vw"}} config]])

```

## Themes

Include `options/options.css` and add one theme class to the options container. Each theme includes its own layout (no need to add `options-label-left` or similar).

| Theme | Description |
|-------|-------------|
| `options-theme-minimal` | Compact, light; label left of edit. |
| `options-theme-minimal-2-col` | Two (label, edit) pairs per row. |
| `options-theme-minimal-n-col` | Pairs per row adapt to container width. |
| `options-theme-card` | Card style with shadow; label left of edit. |
| `options-theme-dark` | Dark background; label left of edit. |
| `options-theme-line` | Label, edit, label, edit in one row; group headers inline. |
| `options-theme-line-groups` | Like line, but group headers on their own row. |

**Example — minimal theme in a narrow sidebar:**

```clojure
[options-ui2 {:class "options-theme-minimal"
              :style {:width "280px"}
              :edit options
              :state state}]
```

# demo

```
cd demo
clj -X:npm-install
clj -X:demo
```

Open Browser at port 8080.