(ns ignorabilis.web.comm.routes)

(def ig-routes ["" [["" :client-routes/home-page]
                    ["/" {""           :client-routes/home-page
                          "skills"     :client-routes/skills-page}]
                    [true :client-routes/not-found-page]]])
