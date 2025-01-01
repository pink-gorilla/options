(ns options.editor.button)

; for ideas for props for button look at:
; https://github.com/knipferrc/tails-ui/blob/master/src/components/Button.re

(defn editor-button [{:keys [_set-fn options]} _current-val]
  (let [{:keys [class style name on-click]
         :or {class ""
              style {}
              name ""}} options]
    [:button
     {:class class
      :style style
      :on-click (fn [_ & _]
                  (when on-click
                    (on-click)))}
     name]))

