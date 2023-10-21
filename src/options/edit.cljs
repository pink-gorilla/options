(ns options.edit
  (:require
   [options.editor.bool]
   [options.editor.button]
   [options.editor.select]
   [options.editor.string]
   [options.editor.view]))

(def bool   options.editor.bool/editor-bool)
(def button options.editor.button/editor-button)
(def select options.editor.select/editor-select)
(def string options.editor.string/editor-string)
(def view   options.editor.view/editor-view)
