(ns theophilusx.yorick.media)

(defn media-left [content & {:keys [class id]}]
  [:aside.media-left {:class [class]
                      :id id}
   content])

(defn media-right [content & {:keys [class id]}]
  [:aside.media-right {:class [class]
                       :id id}
   content])

(defn media [content & {:keys [left right classes media-id content-id]}]
  [:article.media {:class [(:media classes)]
                   :id media-id}
   left
   [:div.media-content {:class [(:content classes)]
                        :id content-id}
    content]
   right])

