# Fuseサンプル - WebサービスのServer実装


<!-- @import "[TOC]" {cmd="toc" depthFrom=2 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [ソフトウェアバージョン](#ソフトウェアバージョン)
- [本サンプルについて](#本サンプルについて)
- [処理概要](#処理概要)
  - [1. cxfEndpoint: orderService](#1-cxfendpoint-orderservice)
  - [2. Camel Route: simple-route](#2-camel-route-simple-route)
- [本サンプルの実行方法](#本サンプルの実行方法)
  - [1. ローカルPCで実行する場合](#1-ローカルpcで実行する場合)
- [ゼロから作成の手順](#ゼロから作成の手順)

<!-- /code_chunk_output -->

- [前提条件](#前提条件)
- [本サンプルについて](#本サンプルについて)
- [処理概要](#処理概要)
  - [1. cxfEndpoint: orderService](#1-cxfendpoint-orderservice)
  - [2. Camel Route: simple-route](#2-camel-route-simple-route)
- [本サンプルの実行方法](#本サンプルの実行方法)
  - [1. ローカルPCで実行する場合](#1-ローカルpcで実行する場合)
- [ゼロから作成の手順](#ゼロから作成の手順)

## ソフトウェアバージョン

- Fuse 7.x (ローカル、またはOpenshift上)
- Spring-boot 2.1
- Java 1.8

## 本サンプルについて

本サンプルでは、[camel-cxfコンポーネント][1]を使って、SOAP Webサービス(Server)の実装方法を示します。Webサービスの開発手法は、Coding-FirstとWSDL-Fisrtの２種類がありますが、
今回は、素早くWebサービスを提供する目的で、Coding-Firstの手法を採用しました。

> SOAP Clientの実装は、[fuse-soap-client-demo][3]を参照してください。

[3]: https://github.com/jian-feng/fuse-soap-client-demo

また、[camel-cxf][1]のオプションが非常に多いですが、今回は初心者向けのため、最低限の設定のみとなっています。他の詳細なオプションは[リンク先][1]の製品ドキュメントをご参照ください。

[1]: https://access.redhat.com/documentation/en-us/red_hat_fuse/7.5/html/apache_camel_component_reference/cxf-component

## 処理概要

### 1. cxfEndpoint: orderService

cxfEndpoint Beanを使って、orderService(Webサービスのインタフェース)をWebサービスに公開します。
```xml
<cxf:cxfEndpoint id="orderService"
  address="http://localhost:9090/order/"
  serviceClass="com.sample.OrderService"/>
```

上記の`com.sample.OrderService`では、Javaインタフェースを使ったWebサービスの定義となっています。
```java
@WebService
public interface OrderService {
  String order(
    @WebParam(name = "product") String product,
    @WebParam(name = "amount") int amount
    );
}
```

これらの設定だけで、camel-cxfはJavaインタフェースの内容を元に、動的にWSDLを生成してくれます。


### 2. Camel Route: simple-route

このCamel Routeは、cxfEndpointからSOAPリクエストを受け取ります。
この時点で、既にSOAPメッセージのボディはPOJOに変換され、`exchange.in.body`にセットされます。

この後、`myTransformer`で3桁の乱数を生成して`exchange.in.body`にセットします。

最後は、cxfEndpointが自動的に、`exchange.in.body`をSOAPメッセージに変換してWebサービスクライアントへ返します。

```xml
<route id="simple-route">
  <from uri="cxf:bean:orderService" />
  <log message=">>> receive: ${body}" />
  <transform>
    <method ref="myTransformer"/>
  </transform>
  <log message=">>> send: ${body}"/>
</route>
```

## 本サンプルの実行方法

### 1. ローカルPCで実行する場合

前提条件:
- Maven 3.x がローカルPCでインストール済みであること
- Maven Remote Repositoryがアクセスできること

実行手順:
1. コマンドプロンプトにて実行します。  
   ```sh
   mvn clean spring-boot:run
   ```

2. コマンドプロンプトでWebサービスが公開されることを確認してください。
   ```
   INFO  o.a.c.w.s.f.ReflectionServiceFactoryBean - Creating Service {http://sample.com/}OrderServiceService from class com.sample.OrderService
   INFO  org.apache.cxf.endpoint.ServerImpl - Setting the server's publish address to be http://localhost:9090/order/
   ```

3. WebサービスのWSDLのを確認します。  
    ブラウザ、若しくはSOAP UIで下記のURLへアクセスしてWSDLを確認できます。  
    URL: http://localhost:9090/order?wsdl

4. Webサービスを呼び出して見ます。  
    Curl、若しくはSOAP UIで下記のエンドポイントへリクエストしてください。
    ```sh
    curl -d @soap-request.xml http://localhost:9090/order
    ```

    レスポンス例として、以下になります。"164"とは、CamelRouteが自動生成した３桁の乱数です。
    ```xml
    <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
      <soap:Body>
        <ns2:orderResponse xmlns:ns2="http://sample.com/">
          <return>164</return>
        </ns2:orderResponse>
      </soap:Body>
    </soap:Envelope>
    ```

5. コマンドプロンプトでCamel Routeのログを確認します。  
    ```console
    INFO  simple-route - >>> receive: abc,10
    INFO  simple-route - >>> send: 164
    ```

## ゼロから作成の手順

以下は本サンプルをゼロから作成する場合の手順を説明します。

※ 本手順の実施には、インターネット接続が必要。

1. Maven archetypeより、Fuseアプリの雛形を生成します。  
    ```sh
    # Config archetype to use
    archetypeVersion=2.2.0.fuse-sb2-750011-redhat-00006
    archetypeCatalog=https://maven.repository.redhat.com/ga/io/fabric8/archetypes/archetypes-catalog/${archetypeVersion}/archetypes-catalog-${archetypeVersion}-archetype-catalog.xml

    # Config Fuse project name to generate
    MY_PROJECT=fuse-soap-server-demo

    # Generate from archetype, which is SpringBoot2 based Camel Application using XML DSL
    mvn org.apache.maven.plugins:maven-archetype-plugin:2.4:generate -B \
      -DarchetypeCatalog=${archetypeCatalog} \
      -DarchetypeGroupId=org.jboss.fuse.fis.archetypes \
      -DarchetypeVersion=${archetypeVersion} \
      -DarchetypeArtifactId=spring-boot-camel-xml-archetype \
      -DgroupId=com.sample \
      -DartifactId=${MY_PROJECT}  \
      -DoutputDirectory=${MY_PROJECT}
    ```

2.  依存ライブラリcamel-cxfとcxf-runtime-transportを追加  

    File: `pom.xml`
    ```xml
    <!-- Camel with CXF on Spring-boot -->
    <dependency>
        <groupId>org.apache.camel</groupId>
        <artifactId>camel-cxf-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>org.apache.cxf</groupId>
        <artifactId>cxf-rt-transports-http-undertow</artifactId>
    </dependency>
    ```

3. WebServiceインターフェイスを作成  
    File: `src/main/java/com/sample/OrderService.java`
    ```java
    @WebService
    public interface OrderService {

      String order(
        @WebParam(name = "product") String product,
        @WebParam(name = "amount") int amount
        );
    }
    ```
    WebServiceインターフェイスの細かいカスタマイズ方法は、以下を参照してください。
    - http://terasolunaorg.github.io/guideline/5.5.1.RELEASE/ja/ArchitectureInDetail/WebServiceDetail/SOAP.html#web
    ==> WebServiceインターフェイスの作成

    - https://cwiki.apache.org/confluence/pages/viewpage.action?pageId=83179
    ==> サービスの実装

4. camel-contextのXMLネームスペースを修正  
    `xmlns:cxf`と`xsi:schemaLocation`を以下のように追加します。  
    File: `src/main/resources/spring/camel-context.xml`
    ```xml
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:cxf="http://camel.apache.org/schema/cxf"
           xsi:schemaLocation="
           http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
           http://camel.apache.org/schema/spring http://camel.apache.org/schema/spring/camel-spring.xsd
           http://camel.apache.org/schema/cxf http://camel.apache.org/schema/cxf/camel-cxf.xsd">
    ```

5. camel-contextへcxfエンドポイントのbean定義を追加  
    File: `src/main/resources/spring/camel-context.xml`  
    ```xml
    <cxf:cxfEndpoint id="orderService"
      address="http://localhost:9090/order/"
      serviceClass="com.sample.OrderService"/>
    ```

6. camel-context内のCamel Routeをすべて削除して、以下のRouteを追加  
    File: `src/main/resources/spring/camel-context.xml`  
    ```xml
    <route id="simple-route">
      <from uri="cxf:bean:orderService" />
      <log message=">>> receive: ${body}" />
      <transform>
        <method ref="myTransformer"/>
      </transform>
      <log message=">>> send: ${body}"/>
    </route>
    ```
