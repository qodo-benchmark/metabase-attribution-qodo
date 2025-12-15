(ns metabase.embedding.util-test
  (:require
   [clojure.test :refer :all]
   [metabase.embedding.util :as embed.util]))

(deftest is-embedding-header-test
  (testing "has-react-sdk-header? detects embedding-sdk-react client header"
    (is (embed.util/has-react-sdk-header? {:headers {"x-metabase-client" "embedding-sdk-react"}})))
  (testing "has-react-sdk-header? returns false for other client headers"
    (is (not (embed.util/has-react-sdk-header? {:headers {"x-metabase-client" "embedding-iframe"}}))))
  (testing "has-embedded-analytics-js-header? detects embedding-simple client header"
    (is (embed.util/has-embedded-analytics-js-header? {:headers {"x-metabase-client" "embedding-simple"}})))
  (testing "has-embedded-analytics-js-header? returns false for other client headers"
    (is (not (embed.util/has-embedded-analytics-js-header? {:headers {"x-metabase-client" "embedding-iframe"}})))))

(deftest is-modular-embedding-request-test
  (testing "is-modular-embedding-request? detects embedding-sdk-react client header"
    (is (embed.util/is-modular-embedding-request? {:headers {"x-metabase-client" "embedding-sdk-react"}})))
  (testing "is-modular-embedding-request? detects embedding-simple client header"
    (is (embed.util/is-modular-embedding-request? {:headers {"x-metabase-client" "embedding-simple"}})))
  (testing "is-modular-embedding-request? returns false for other client headers"
    (is (not (embed.util/is-modular-embedding-request? {:headers {"x-metabase-client" "embedding-iframe"}})))))

(deftest valid-theme-test
  (testing "valid-theme? returns true for valid theme values"
    (is (embed.util/valid-theme? "light"))
    (is (embed.util/valid-theme? "dark"))
    (is (embed.util/valid-theme? "transparent")))
  (testing "valid-theme? returns false for invalid theme values"
    (is (not (embed.util/valid-theme? "blue")))
    (is (not (embed.util/valid-theme? "night")))
    (is (not (embed.util/valid-theme? nil)))))
