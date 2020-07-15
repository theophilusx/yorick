- [Yorick](#sec-1)
  - [Key Objectives](#sec-1-1)
  - [Installation](#sec-1-2)
  - [Using the API](#sec-1-3)
    - [Namespaces](#sec-1-3-1)
    - [Optional Keyword Arguments](#sec-1-3-2)
    - [HTML Attribute Support](#sec-1-3-3)
    - [Layout](#sec-1-3-4)
    - [Working with Classes](#sec-1-3-5)
    - [Component Contents](#sec-1-3-6)
    - [Managing State](#sec-1-3-7)
    - [API Namespaces](#sec-1-3-8)
  - [License](#sec-1-4)

# Yorick<a id="sec-1"></a>

*Yorick* is a library of reagent components which use the [Bulma](https://bulma.io) framework for CSS styling. Bulma is a full featured pure CSS framework which does not include any Javascript. This library builds on this and adds necessary ClojureScript to create high level Reagent components to represent functionality commonly required when developing web based applications.

Yorick has been developed using the excellent [shadow-cljs](https://github.com/thheller/shadow-cljs) library, but can be used with any ClojureScript build environment, such as *lein*, *CLI tools* or *boot*.

## Key Objectives<a id="sec-1-1"></a>

-   High level API. Make it easy to do the easy stuff
-   Flexible. Make it possible to do the hard stuff
-   Interchangeable. Easily combine components and compose components to create new components
-   Consistent and intuitive. Once you get use to the library, be able to make reasonable guesses regarding available arguments and options
-   Data driven. Whenever possible, allow definition of components to be done in basic data structures like *maps* and *vectors*.

## Installation<a id="sec-1-2"></a>

Until we add this library to clojars (in the near future), you will need to clone this repository into your project. All the necessary code is in the `src` tree. There is also a simple *demo* application in the `demo` tree, which shows how to use some of the components and what they look like when rendered. To run the demo, do the following

    git clone https://github.com/theophilusx/yorick .
    cd yorick
    npm install
    npx shadow-cljs server build demo

Then visit [demo page](http://localhost:8080)

## Using the API<a id="sec-1-3"></a>

### Namespaces<a id="sec-1-3-1"></a>

The *Yorick* library is broken up into several namespaces representing different functionality. Rather than having a single *core* namespace, you load the namespace with the specific functionality you want. Currently, the following namespaces are defined:

-   ****theophilusx.yorick.basic**:** A collection of basic utility components:
    -   **a:** a simple anchor component
    -   **img:** a simple image component
    -   **render-vec:** a simple component for rendering vectors of data
    -   **render-map:** a simple component for rendering maps of data
    -   **breadcrumbs:** a simple breadcrumbs component to provide link breadcrumbs
    -   **notification:** a simple notification component
-   ****theophilusx.yorick.card**:** a basic card presentation component
-   **\*theophilusx.yorick.icon:** components to manage display of icons
-   **\*theophilusx.yorick.input:** a collection of user input components
    -   **field:** a general purpose input field component
    -   **horizontal-field:** a general purpose horizontal input field
    -   **input:** a basic text input component
    -   **input-field:** a text input component wrapped in an input field
    -   **checkbox:** a basic checkbox component
    -   **radio:** a basic radio button component
    -   **button:** a basic button component
    -   **editable-field:** an editable field component
    -   **textarea:** a basic text area component
    -   **select:** a select box component
    -   **select-field:** a select box component wrapped in a field component
    -   **file:** a file selection component
    -   **search:** a basic search box component
    -   **range-field:** a range input component wrapped in a field component
    -   **number-input:** an input component for numbers
    -   **number-field:** a number-input component wrapped in a field component
-   ****theophilusx.yorick.media**:** a basic and flexible media presentation component
-   ****theophilusx.yorick.modal**:** a modal overlay window component
-   ****theophilusx.yorick.navbar**:** a basic navigation bar component
-   ****theophilusx.yorick.paginate**:** a basic pagination component
-   ****theophilusx.yorick.sidebar**:** a side menu (vertical) component
-   ****theophilusx.yorick.store**:** various functions to manipulate Reagent atoms for data storage
-   ****theophilusx.yorick.table**:** a component to render data in HTML tables
-   ****theophilusx.yorick.toolbars**:** a basic component for rendering a toolbar
-   ****theophilusx.yorick.utils**:** a collection of useful utility functions

### Optional Keyword Arguments<a id="sec-1-3-2"></a>

The library makes extensive use of keyword arguments. This has the advantage of making simple default function calls very clean and easy to do, but provides an *escape hatch* when you need to do more complicated things. The disadvantage is that ifyou need to do lots of complicated things, the function calls can become very long. However, if you do need to do such complicated things a lot, it is probably a sign you need to create higher level abstractions to restrict the complexity to well defined points.

### HTML Attribute Support<a id="sec-1-3-3"></a>

Many HTML entities support large numbers of attributes. To support this, many components allow for an `:attrs` keyword, which consists of HTML attribute names as keywords and an associated string value. This map will be merged into the definition of the component.

### Layout<a id="sec-1-3-4"></a>

The *Bulma* CSS framework is based on **flexbox**, providing a simple, fast and flexible grid system to layout your web content. Bulma uses classes to support containers, responsive columns, levels and other layout components. Unlike other *Reagent* component libraries, *Yorick* does not attempt to provide any additional layout functionality. All the standard *Bulma* classes used to manage layout are compatible with the components provided by *Yorick*.

To get the most out of *Yorick* you will need to have a basic familiarity with *Bulma*. The good news is that *Bulma* is clear, simple and easy to learn. Use *Bulma* classes like `columns`, `level` and `tiles` to manage your layout and *bulma* helper classes to style the *Yorick* components. s

### Working with Classes<a id="sec-1-3-5"></a>

The standard way to modify the appearance of a component is by adding CSS classes. As *bulma* is a pure CSS framework, all Bulma features are controlled by adding Bulma specific class names to elements. See the Bulma documentation for details on what class names are supported for each element.

Many components are actually made up of multiple HTML elements and applying specific classes to each of these elements can become untidy and difficult to maintain. To handle this level of complexity, this library uses the following conventions

1.  When these is just a single element in the component, allow a keyword argument of `:class`. This argument can have either a string value where the string lists the CSS classes to be added to the element or a vector, which contains values that will resolve to a string (or nil).

2.  When the component is a composition of HTML elements, a `:classes` argument is supported. The value of this argument should be a `map` where the keys are keywords representing HTML elements and the value associated with the key is either a string containing CSS class names or a vector which contains values that will resolve to CSS class name strings or nil.

1.  Examples

    The \`a\` component is a basic component that renders an HTML link element. As it only has one element, the `:class` keyword argument is supported. For example
    
    ```clojurescript
    [:p "This is a paragraph with a link of " [a "link name" :class "button"]
     ". It is a button link"]
    ```
    
    This will generate HTML which looks like
    
    ```html
    <p>This is a paragraph with a link of 
    <a href="#" class="button">link name</a>. It is a button link
    </p>
    ```
    
    Alternatively, you could use a vector for the value of `:class`. This can be very useful when you want to add something dynamic i.e. which calculates the value of the class name to add. For example
    
    ```clojurescript
    [:p "This is a link with a dynamic class name "
     [a "link name" :class ["button"
                            (when (= (:link-state @state) :active)
                              "is-active")]
      ". It is an active button link"]]
    ```
    
    The above uses a `when` conditional to add the *is-active* class if the value of the key `:link-state` is `:active`. If it is not `:active`, it will add `nil`, which will be ignored. So if the value is `:active` the result will be
    
    ```html
    <p>
      This is a link with a dynamic class name  
      <a href="#" class="button is-active">link name</a>
      . It is an active button link
    </p>
    ```
    
    The `input-field` component is an example of a component which is made up of multiple HTML elements. There is an outer `:div` element for the field, a `:label` element for the field label, a `:control` field to contain the final `:input` element. Therefore, the `input-field` element supports the `:classes` keyword argument, which should have a `map` as the value. This map should have keys for one or more of the inner elements i.e. `:field`, `:label`, `:control` or `:input`. You only need to add keys for the elements you want to add classes to. The value of each key can be either a string of class names or a vector with components that will resolve to a class name string or nil.

### Component Contents<a id="sec-1-3-6"></a>

In most cases, a component is really just a wrapper around other components or HTML elements. An element can be as simple as just a string or as complex as a nested HTML table. In most cases, the components provided by *Yorick* only accept a single value for the *body* argument of the component. However, sometimes you might want to provide multiple values. To enable passing multiple values into a component, it is necessary to wrap it in either an explicit `:div` element or you can use the handy `:<>` shortcut. This is also a requirement of `React` - the value passed into a `React` component must be either a vector or a function which returns a vector. You cannot just pass in a nested vector, so something like

```clojurescript
[field [[button "Save"]
        [Button "Cancel"]]]
```

won't work. It will generate an error about invalid hiccup. However the following two approaches will work just fine.

```clojurescript
[field [:div
        [button "Save"]
        [button "Cancel"]]]

[field [:<>
        [button "Save"]
        [button "Cancel"]]]
```

The first will wrap the two button components in a `<div>`, which is usually fine. The second will enable `React` to handle the two button components and may avoid the addition of an explicit `<div>` element.

### Managing State<a id="sec-1-3-7"></a>

The `theophilusx.yorick.store` namespace contains functions to assist in managing Reagent `atoms`. In Reagent, state is typically managed inside special `atoms`. Reagent components know which atoms they reference. When a value inside a referenced atom is updated, Reagent knows that the associated component may need to be re-rendered to reflect the new value.

Some components within *Yorick* use local atoms to store local state relevant to that component. The `theophilusx.yorick.store` namespace also includes a global state atom called `global-state`. This atom can be used to store state information which needs to be shared between components.

The `theophilusx.yorick.store` namespace contains functions to insert, update, retrieve and delete values in a Reagent atom. The global state atom is called `theophilusx.yorick.global-state` and is created when the namespace is first loaded. It uses the `defonce` macro to define the atom, so subsequent re-loads of the namespace do not result in redefinition of the atom. The atom is initialised as an empty `map`.

In general, *Yorick* uses ClojureScript `map` structures for state atoms. Values are stored in the atom by providing a *path* into the atom i.e. a list of keys. To make it easier to work with these paths, Yorick uses the convention of defining paths as `keywords` where a period `(.)` in the keyword is interpreted as a path separator. For example, the keyword `:ui.page.state` represents the path `[:ui :page :state]`. The `theophilusx.yorick.utils` namespace contains a function called `spath`, which accepts a single keyword argument. It will parse the keyword and return a vector of the keys the keyword represents i.e.

    (spath :ui.page.state) => [:ui :page :state

### API Namespaces<a id="sec-1-3-8"></a>

The API uses separate namespaces for most components. The `core` namespace is a wrapper around most of the component namespaces. If your going to use most of the components provided by *Yorick*, your best off just loading the core namespace. However, If you only want to use specific components, you can just load the associated namespace for that component. The following namespaces are used -

| Namespace                   | Purpose                                    |
|--------------------------- |------------------------------------------ |
| theophilusx.yorick.basic    | Very simple and basic components           |
| theophilusx.yorick.card     | A `card` component                         |
| theophilusx.yorick.icon     | A simple icon component                    |
| theophilusx.yorick.input    | A collection of input related components   |
| theophilusx.yorick.media    | A flexible media component                 |
| theophilusx.yorick.modal    | A modal window component                   |
| theophilusx.yorick.navbar   | A navigation bar component                 |
| theophilusx.yorick.paginate | A pagination component                     |
| theophilusx.yorick.sidebar  | A sidebar menu component                   |
| theophilusx.yorick.table    | An HTML table component                    |
| theophilusx.yorick.toolbar  | A simple toolbar component                 |
| theophilusx.yorick.utils    | A collection of useful utility functions   |
| theophilusx.yorick.store    | A collection of state management functions |

## License<a id="sec-1-4"></a>

Copyright &copy; 2020 Tim Cross

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
