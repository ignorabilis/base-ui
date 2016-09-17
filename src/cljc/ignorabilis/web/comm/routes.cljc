(ns ignorabilis.web.comm.routes)

(def ig-routes ["" [["" ::home-page]
                    ["/" {""           ::home-page
                          "skills"     ::skills-page
                          "experience" ::experience-page}]
                    [true ::not-found-page]]])
