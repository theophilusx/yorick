(ns yorick-demo.intro
  (:require [theophilusx.yorick.basic :as basic]))


(defn intro-page []
  [:<>
   [:p "This site demonstrates the use of " [:strong "Yorick"] ", a library of "
    [:code "Reagent"] " based web components useful in the implementation of web based
applications. " [basic/a "Reagent" :href "https://reagent-project.github.io/"]
    " is a minimalistic ClojureScript interface into the " [basic/a "React" :href "https://reactjs.org/"]
    " framework."]
   [:p "Yorick uses the " [basic/a "Bulma" :href "https://bulma.io"] " CSS framework for styling components and
managing layout. The components provided by Yorick can be modified using Bulma classes and the overall style
of components can be modified by customizing the Bulma styles (a supported feature of BUlma)."]
   [:p "To get the most out of Yorick, a basic familiarity of Bulma is recommended. As
Bulma is a purely CSS based framework, gaining familiarity is not difficult."]
   [:p "Familiarity with React is not required to use Yorick. However, a basic understanding of Reagent
is likely useful, but not required."]
   [:h4.title.is-4 "Changes in Version 2"]
   [:p "The Yorick API has changed significantly with version 2. Overall, the API has been
simplified. Many of the options provided in the first version proved to be unnecessary or seldom
used in practice. It was decided that having clarity and simplicity of code was more important than
maintaining functionality which was unnecessary and which only worked to make the code more complex and difficult to maintain."]
   [:p "One specific change has been to remove the ability, or need in some cases, to specify different
Reagent atoms for storing state. The new API has removed state storage where unnecessary and now all components which do require
some form of state all use a global store. The " [:code "theophilusx.yorick/store"] " namespace provides
and API for managing and interacting with this global store."]])
