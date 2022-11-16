((nil . ((cider-preferred-build-tool       . clojure-cli)
         (cider-clojure-cli-global-options . "-A:dev")
         (cider-default-cljs-repl          . custom)
         (cider-custom-cljs-repl-init-form . "(user/cljs-repl)")
         (eval . (progn
                   (make-variable-buffer-local 'cider-jack-in-nrepl-middlewares)
                   (add-to-list 'cider-jack-in-nrepl-middlewares "shadow.cljs.devtools.server.nrepl/middleware"))))))
