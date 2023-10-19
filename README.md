# options [![GitHub Actions status |pink-gorilla/options](https://github.com/pink-gorilla/options/workflows/CI/badge.svg)](https://github.com/pink-gorilla/options/actions?workflow=CI)[![Clojars Project](https://img.shields.io/clojars/v/org.pinkgorilla/options.svg)](https://clojars.org/org.pinkgorilla/options)


## features
- many functions can need options that can be specified by the user
- the idea is to define a "spec" whcih allows the ui to be generated automatically. 
- options is a map.
- available specs are:
  - :bool 
  - :string 
  - :select (from a list, possibly with a name that is different to the selected value) 

# example

```
  [options-ui {:class "bg-blue-300 grid grid-cols-2"
               :style {;:width "50vw"
                       ;:height "40vh"
                       :border "3px solid green"}}
    {:current {; select
               :year 2023
               :client 2
               :pet :hamster
               ; bool
               :run-parallel true
               :debug? false
               ; string
               :search ""
               }
     :options {:year {:name "Year"
                      :spec (range 2018 2024)}
               :client {:name "Client"
                        :spec [{:id 1 :name "Batman"}
                               {:id 2 :name "Robin"}
                               {:id 3 :name "Harry Potter"}
                               {:id 4 :name "Dumbledor"}
                               {:id 5 :name "The Hulk"}]
                        :class "placeholder-gray-400 text-gray-700 relative bg-white rounded text-sm border border-gray-400 outline-none focus:outline-none focus:shadow-outline"
                        }
               :pet {:name "Pet"
                     :spec [:cat :dog :parrot :hamster]
                     :class "placeholder-gray-400 text-gray-700 relative bg-white rounded text-sm border border-gray-400 outline-none focus:outline-none focus:shadow-outline"}

               :run-parallel {:name "Run in Parallel?"
                              :spec :bool
                              :class "pt-0 px-2 py-1 placeholder-gray-400 text-gray-700 relative bg-white rounded text-sm border border-gray-400 outline-none focus:outline-none focus:shadow-outline"
                              }
               :debug? {:name "Debug Mode"
                        :spec :bool}
               :search {:name "SearchBox"
                        :spec :string
                        :class "px-2 py-1 placeholder-gray-400 text-gray-700 relative bg-white bg-white rounded text-sm border border-gray-400 outline-none focus:outline-none focus:shadow-outline"
                        }
               }}]
```

# demo

The demo uses the extension manager from goldly to add options to goldly.

```
cd demo
clj -X:demo:npm-install
clj -X:demo:compile
clj -X:demo
```

Open Browser at port 8080.