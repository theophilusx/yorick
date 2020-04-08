
# Table of Contents

1.  [Yorick](#org3632e02)
    1.  [Key Objectives](#org7ff754e)
    2.  [Installation](#org044bfa4)
    3.  [Using the API](#org5e64b54)
        1.  [Layout](#orgc132760)
        2.  [Working with Classes](#orgf0030e5)
        3.  [Component Contents](#orgdfce66e)
        4.  [Managing State](#org77089e0)
        5.  [API Namespaces](#org7b54233)
    4.  [License](#org4a7e8e1)


<a id="org3632e02"></a>

# Yorick

*Yorick* is a library of reagent components which use the [Bulma](https://bulma.io) framework for
CSS styling. Bulma is a full featured pure CSS framework which does not include
any Javascript. This library builds on this and adds necessary Javascript to
create high level Reagent components to represent functionality commonly
required when developing web based applications. 

Yorick has been developed using the excellent [shadow-cljs](https://github.com/thheller/shadow-cljs) library, but can be
used with any ClojureScript build environment, such as *lein*, *CLI tools* or
*boot*.


<a id="org7ff754e"></a>

## Key Objectives

-   High level API. Make it easy to do the easy stuff
-   Flexible. Make it possible to do the hard stuff
-   Interchangeable. Easily combine components and compose components to create new components
-   Consistent and intuitive. Once you get use to the library, be able to make reasonable guesses regarding available arguments and options
-   Data driven. Whenever possible, allow definition of components to be done
    in basic data structures like *maps* and *vectors*.


<a id="org044bfa4"></a>

## Installation

Until we add this library to clojars (in the near future), you will need to
clone this repository into your project. All the necessary code is in the
`src` tree. There is also a simple *demo* application in the `demo` tree
which shows how to use some of the components and what they look like when
rendered. To run the demo, do the following

    git clone https://github.com/theophilusx/yorick .
    cd yorick
    npm install
    npx shadow-cljs server build demo

Then visit [demo page](http://localhost:8080) 


<a id="org5e64b54"></a>

## Using the API

The library makes extensive use of keyword arguments. This has the advantage
of making simple default function calls very clean and easy to do, but
provides an *escape hatch* when you need to do more complicated things. The
disadvantage is that ifyou need to do lots of complicated things, the
function calls can become very long. However, if you do need to do such
complicated things a lot, it is probably a sign you need to create higher
level abstractions to restrict the complexity to well defined points.

Many HTML entities have large numbers of attributes that can be used with
them. However, most of the time, only a few of these are necessary. This
library tries to add sensible defaults for many of these attributes and
allows you to add them when necessary via *keyword* arguments. There are also
some *tricks* you can use to get around some common issues that come up when
working with components.


<a id="orgc132760"></a>

### Layout

The *Bulma* CSS framework is based on **flexbox**, providing a simple, fast
and flexible grid system to layout your web content. Bulma uses classes to
support containers, responsive columns, levels and other layout components.
Unlike other *Reagent* component libraries, *Yorick* does not attempt to
provide any additional layout functionality. All the standard *Bulma*
classes used to manage layout are compatible with the components provided by
*Yorick*. 

To get the most out of *Yorick* you will need to have a basic familiarity
with *Bulma*. The good news is that *Bulma* is clear, simple and easy to
learn. Use *Bulma* classes like `columns`, `level` and `tiles` to manage
your layout and *bulma* helper classes to style the *Yorick* components. s


<a id="orgf0030e5"></a>

### Working with Classes

The standard way to modify the appearance of a component is by adding CSS
classes. As *bulma* is a pure CSS framework, all Bulma features are
controlled by adding Bulma specific class names to elements. See the Bulma
documentation for details on what class names are supported for each
element. 

Many components are actually made up of multiple HTML
elements and applying specific classes to each of these elements can become
untidy and difficult to maintain. To handle this level of complexity, this
library uses the following conventions

1.  When these is just a single element in the component, allow a keyword
    argument of `:class`. This argument can have either a string value where
    the string lists the CSS classes to be added to the element or a vector,
    which contains values that will resolve to a string (or nil).

2.  When the component is a composition of HTML elements, a `:classes`
    argument is supported. The value of this argument should be a `map` where
    the keys are keywords representing HTML elements and the value associated
    with the key is either a string containing CSS class names or a vector
    which contains values that will resolve to CSS class name strings or nil.

1.  Examples

    The \`a\` component is a basic component that renders an HTML link element. As it only has one element, the `:class` keyword argument is supported. For example
    
        [:p "This is a paragraph with a link of " [a "link name" :class "button"]
         ". It is a button link"]
    
    This will generate HTML which looks like
    
        <p>This is a paragraph with a link of 
        <a href="#" class="button">link name</a>. It is a button link
        </p>
    
    Alternatively, you could use a vector for the value of `:class`. This can be
    very useful when you want to add something dynamic i.e. which calculates the
    value of the class name to add. For example
    
        [:p "This is a link with a dynamic class name "
         [a "link name" :class ["button"
                                (when (= (:link-state @state) :active)
                                  "is-active")]
          ". It is an active button link"]]
    
    The above uses a `when` conditional to add the *is-active* class if the
    value of the key `:link-state` is `:active`. If it is not `:active`, it will
    add `nil`, which will be ignored. So if the value is `:active` the result
    will be
    
        <p>
          This is a link with a dynamic class name  
          <a href="#" class="button is-active">link name</a>
          . It is an active button link
        </p>
    
    The `input-field` component is an example of a component which is made up of
    multiple HTML elements. There is an outer `:div` element for the field, a
    `:label` element for the field label, a `:control` field to contain the
    final `:input` element. Therefore, the `input-field` element supports the
    `:classes` keyword argument, which should have a `map` as the value. This
    map should have keys for one or more of the inner elements i.e. `:field`,
    `:label`, `:control` or `:input`. You only need to add keys for the elements
    you want to add classes to. The value of each key can be either a string of
    class names or a vector with components that will resolve to a class name
    string or nil. 


<a id="orgdfce66e"></a>

### Component Contents

In most cases, a component is really just a wrapper around other components
or HTML elements. An element can be as simple as just a string or as complex
as a nested HTML table. In most cases, the components provided by *Yorick*
only accept a single value for the *body* argument of the component.
However, sometimes you might want to provide multiple values. To enable
passing multiple values into a component, it is necessary to wrap it in
either an explicit `:div` element or you can use the handy `:<>` shortcut.
This is also a requirement of `React` - the value passed into a `React`
component must be either a vector or a function which returns a vector. You
cannot just pass in a nested vector, so something like 

    [field [[button "Save"]
            [Button "Cancel"]]]

won't work. It will generate an error about invalid hiccup. However the
following two approaches will work just fine. 

    [field [:div
            [button "Save"]
            [button "Cancel"]]]
    
    [field [:<>
            [button "Save"]
            [button "Cancel"]]]

The first will wrap the two button components in a `<div>`, which is usually
fine. The second will enable `React` to handle the two button components and
may avoid the addition of an explicit `<div>` element. 


<a id="org77089e0"></a>

### Managing State

The `theophilusx.yorick.store` namespace contains functions to assist in
managing Reagent `atoms`. In Reagent, state is typically managed inside
special `atoms`. Reagent components know which atoms they reference. When a
value inside a referenced atom is updated, Reagent knows that the associated
component may need to be re-rendered to reflect the new value. 

Some components within *Yorick* use local atoms to store local state
relevant to that component. The `theophilusx.yorick.store` namespace also
includes a global state atom called `global-state`. This atom can be used to
store state information which needs to be shared between components. 

The `theophilusx.yorick.store` namespace contains functions to insert,
update, retrieve and delete values in a Reagent atom. The global state atom
is called `theophilusx.yorick.global-state` and is created when the
namespace is first loaded. It uses the `defonce` macro to define the atom,
so subsequent re-loads of the namespace do not result in redefinition of the
atom. The atom is initialised as an empty `map`. 

In general, *Yorick* uses ClojureScript `map` structures for state atoms.
Values are stored in the atom by providing a *path* into the atom i.e. a
list of keys. To make it easier to work with these paths, Yorick uses the
convention of defining paths as `keywords` where a period `(.)` in the
keyword is interpreted as a path separator. For example, the keyword
`:ui.page.state` represents the path `[:ui :page :state]`. The
`theophilusx.yorick.utils` namespace contains a function called `spath`,
which accepts a single keyword argument. It will parse the keyword and
return a vector of the keys the keyword represents i.e. 

    (spath :ui.page.state) => [:ui :page :state


<a id="org7b54233"></a>

### API Namespaces

The API uses separate namespaces for most components. The `core` namespace
is a wrapper around most of the component namespaces. If your going to use
most of the components provided by *Yorick*, your best off just loading the
core namespace. However, If you only want to use specific components, you
can just load the associated namespace for that component. The following
namespaces are used -

<table>


<colgroup>
<col  class="org-left">

<col  class="org-left">
</colgroup>
<thead>
<tr>
<th scope="col" class="org-left">Namespace</th>
<th scope="col" class="org-left">Purpose</th>
</tr>
</thead>

<tbody>
<tr>
<td class="org-left">theophilusx.yorick.basic</td>
<td class="org-left">Very simple and basic components</td>
</tr>


<tr>
<td class="org-left">theophilusx.yorick.card</td>
<td class="org-left">A `card` component</td>
</tr>


<tr>
<td class="org-left">theophilusx.yorick.icon</td>
<td class="org-left">A simple icon component</td>
</tr>


<tr>
<td class="org-left">theophilusx.yorick.input</td>
<td class="org-left">A collection of input related components</td>
</tr>


<tr>
<td class="org-left">theophilusx.yorick.media</td>
<td class="org-left">A flexible media component</td>
</tr>


<tr>
<td class="org-left">theophilusx.yorick.modal</td>
<td class="org-left">A modal window component</td>
</tr>


<tr>
<td class="org-left">theophilusx.yorick.navbar</td>
<td class="org-left">A navigation bar component</td>
</tr>


<tr>
<td class="org-left">theophilusx.yorick.paginate</td>
<td class="org-left">A pagination component</td>
</tr>


<tr>
<td class="org-left">theophilusx.yorick.sidebar</td>
<td class="org-left">A sidebar menu component</td>
</tr>


<tr>
<td class="org-left">theophilusx.yorick.table</td>
<td class="org-left">An HTML table component</td>
</tr>


<tr>
<td class="org-left">theophilusx.yorick.toolbar</td>
<td class="org-left">A simple toolbar component</td>
</tr>


<tr>
<td class="org-left">theophilusx.yorick.utils</td>
<td class="org-left">A collection of useful utility functions</td>
</tr>


<tr>
<td class="org-left">theophilusx.yorick.store</td>
<td class="org-left">A collection of state management functions</td>
</tr>
</tbody>
</table>


<a id="org4a7e8e1"></a>

## License

Copyright &copy; 2020 Tim Cross

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.

