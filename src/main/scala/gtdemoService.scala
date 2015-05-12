package tutorial

import java.io.File

import geotrellis._
import geotrellis.render.ColorRamps
import geotrellis.source.RasterSource
import geotrellis.services.ColorRampMap

import spray.http._
import spray.routing.HttpService

trait gtdemoService extends HttpService {
  val staticPath: String

  def rootRoute = pathSingleSlash {
    redirect("index.html", StatusCodes.Found)
  }

  def pingRoute = path("ping") {
    get {
      println("ping")
      println("pong1!")
      complete("pong2!")
    }
  }

  def helloRoute = path("hello") {
    get {
      complete("Hello, world!")
    }
  }

  def getRoute =
    get {
      pathPrefix("get") {
        path("json") {
          respondWithMediaType(MediaTypes.`application/json`) {
            complete( s"""{firstName: "John", lastName: "Smith"}""")
          }
        } ~
        path("png") {
          val fileName = "img/gtdemo.png";
          val filePath = staticPath + "/" + fileName;
          //
          val pngFile = new File(filePath)
          getFromFile(filePath)
        } ~
        path("geo" / Segment )  { slug =>
          println(slug)
          val r = RasterSource(slug)
          val rendered = r.renderPng(ColorRamps.BlueToRed)
          rendered.run match {
            case process.Complete(img, h) => respondWithMediaType(MediaTypes.`image/png`) { complete(img) }
            case process.Error(message, trace) =>
              println(message)
              println(trace)
              println(r)
              failWith(new RuntimeException(message))
          }
        } ~
        path("colorramps") {
          complete(ColorRampMap.getJson)
        } ~
        //http://localhost:8777/get/map?SERVICE=WMS&REQUEST=GetMap&VERSION=1.1.1&LAYERS=default&STYLES=&FORMAT=image%2Fpng&TRANSPARENT=true&HEIGHT=256&WIDTH=256&SRS=EPSG%3A3857&BBOX=-626172.1357121639,6887893.4928338,-313086.06785608194,7200979.560689885
        path("map") {
          parameters("SERVICE", "REQUEST", "VERSION", "LAYERS", "STYLES", "FORMAT", "TRANSPARENT"
            , "HEIGHT".as[Int], "WIDTH".as[Int], "SRS", "BBOX" /* no Option*/) {
            (service, request, version, layersString, styles, format, transparent, cols /*height*/ , rows /*width*/ , srs, bbox)
            => var msg = "map:" + " service=" + service + " request=" + request +
              " version=" + version + " layers=" + layersString + " styles=" + styles +
              " format=" + format + " transparent=" + transparent + " cols=" + cols + " rows =" + rows +
              " srs=" + srs + " bbox=" + bbox
              //println(msg)
              println(bbox)

              val extent = Extent.fromString(bbox)

              val rasterExtent = RasterExtent(extent, cols, rows)

              val arg="SBN_farm_mkt"

              val rasterSource = RasterSource(arg,rasterExtent)

              val r = rasterSource.renderPng(ColorRamps.GreenToRedOrange)

              r.run match {
                case process.Complete(img, h) => respondWithMediaType(MediaTypes.`image/png`) { complete(img) }
                case process.Error(message, trace) =>
                  println(message)
                  println(trace)
                  println(r)
                  failWith(new RuntimeException(message))
              }
          }
        }
      }
    }

  def dataRoute = pathPrefix("data") {
    getFromDirectory("data")
  }

  def htmlRoute = pathPrefix("html") {
    getFromDirectory(staticPath)
  }

  def staticRoute = pathPrefix("") {
    getFromDirectory(staticPath)
  }

  def gtdemoRoute = rootRoute ~ pingRoute ~ helloRoute ~ getRoute ~ dataRoute ~ htmlRoute ~ staticRoute
}