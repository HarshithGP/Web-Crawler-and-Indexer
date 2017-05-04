

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller {
	 public static void main(String[] args) throws Exception {
		 System.setProperty("jsse.enableSNIExtension", "false");
		 String crawlStorageFolder = "data/crawl";
		 int numberOfCrawlers = 7;
		 int PolitenessDelay = 100;

		 int maxDepthOfCrawling = 16;
		 int maxPagesToFetch = 20000;
		 int maxDownloadSize = 29999999;
		 
		 boolean includeBinaryContent = true;
		 
		 
		 CrawlConfig config = new CrawlConfig();
		 config.setCrawlStorageFolder(crawlStorageFolder);
		 config.setMaxPagesToFetch(maxPagesToFetch);
		 config.setConnectionTimeout(10000);
		 config.setPolitenessDelay(PolitenessDelay);
		 config.setMaxDepthOfCrawling(maxDepthOfCrawling);
		 config.setMaxDownloadSize(maxDownloadSize);
		 config.setIncludeBinaryContentInCrawling(includeBinaryContent);
		 
		 /*
		 * Instantiate the controller for this crawl.
		 */
		 PageFetcher pageFetcher = new PageFetcher(config);
		 RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		 RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		 CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
		 /*
		 * For each crawl, you need to add some seed urls. These are the first
		 * URLs that are fetched and then the crawler starts following links
		 * which are found in these pages
		 */
		 controller.addSeed("http://www.nbcnews.com/");
		 
		 (String domain: crawlDomains){
			 controller.addSeed(domain);
		 }
		 
		 MyCrawler.configure(crawlStorageFolder);
		 /*
		 * Start the crawl. This is a blocking operation, meaning that your code
		7
		 * will reach the line after this only when crawling is finished.
		 */
		 controller.start(MyCrawler.class, numberOfCrawlers);
		 
		 List<Object> dataList = controller.getCrawlersLocalData();
		 List<CrawledData> crawledDataList = new ArrayList<CrawledData>();
		 for(Object d : dataList){
			 crawledDataList.add((CrawledData)d);
		 }
		 
		 FileWriter fetchFile = new FileWriter("fetch_NBCnews.csv");
		 fetchFile.append("URL,StatusCode\n");
		 fetchFile.flush();
		 fetchFile.close();
		 
		 FileWriter visitedFile = new FileWriter("visit_NBCnews.csv");
		 visitedFile.append("URL,Size,Outlinks,Content-Type\n");
		 visitedFile.flush();
		 visitedFile.close();
		 
		 FileWriter urlFile = new FileWriter("urls_NBCnews.csv");
		 urlFile.append("URL,Location\n");
		 urlFile.flush();
		 urlFile.close();
		 
		 fetchFile = new FileWriter("fetch_NBCnews.csv", true);
		 visitedFile = new FileWriter("visit_NBCnews.csv", true);
		 urlFile = new FileWriter("urls_NBCnews.csv", true);
		 FileWriter report = new FileWriter("CrawlerReport_NBCnews.txt");
		 int fetchesAttempted = 0;
		 int fetchesSucceeded = 0;
		 int fetchesAborted = 0;
		 int fetchesFailed = 0;
		 int totalUrls = 0;
		 int uniqueURLInWebsite = 0;
		 int uniqueURLOutsideWebsite = 0;
		 //int uniqueURLOutsideUSC = 0;
		 int statusOK = 0;
		 int statusMoved = 0;
		 int statusUnauth = 0;
		 int statusForbidden = 0;
		 int statusNotFound = 0;
		 int size1 = 0;
		 int size2 = 0;
		 int size3 = 0;
		 int size4 = 0;
		 int size5 = 0;
		 int typeText = 0;
		 int typeGif = 0;
		 int typePng = 0;
		 int typePdf = 0;
		 int typeJpeg = 0;
		 int typeDoc = 0;
		 
		 Set<String> uniqueURLInWebsiteSet = new HashSet<String>();		 
		 Set<String> uniqueURLOutsideWebsiteSet = new HashSet<String>();
		 Set<String> uniqueURLOutsideUSCSet = new HashSet<String>();
		 
		 for(CrawledData crawledData : crawledDataList){
			 List<FetchData> fetchDataList = crawledData.getFetchDataList();
			 for(FetchData fetchData : fetchDataList){
				 fetchFile.append(fetchData.getUrl() + "," + fetchData.getHTTPStatusCode() + "\n");
			 }
			 
			 List<VisitedData> visitedDataList = crawledData.getVisitedDataList();
			 for(VisitedData visitedData : visitedDataList){
				 visitedFile.append(visitedData.getUrl() + "," + visitedData.getContentLength() + "," + visitedData.getOutlinkSize() + "," + visitedData.getType() + "\n");
			 }
			 
			 List<UrlData> urlDataList = crawledData.getUrlDataList();
			 for(UrlData urlData : urlDataList){
				 urlFile.append(urlData.getUrlProcessed() + "," + getLocationString(urlData.getLocation()) + "\n");
			 }
			
			fetchesAttempted += crawledData.getFetchesAttempted();
			fetchesSucceeded += crawledData.getFetchesSucceeded();
			fetchesAborted += crawledData.getFetchesAborted();
			fetchesFailed += crawledData.getFetchesFailed();
			totalUrls+= crawledData.getTotalUrls();
			uniqueURLInWebsiteSet.addAll(crawledData.getUniqueURLInWebsite());
			uniqueURLOutsideWebsiteSet.addAll(crawledData.getUniqueURLOutsideWebsite());
			uniqueURLOutsideUSCSet.addAll(crawledData.getUniqueURLOutsideUSC());
			statusOK += crawledData.getStatusOK();
			statusMoved += crawledData.getStatusMoved();
			statusUnauth += crawledData.getStatusUnauth();
			statusForbidden += crawledData.getStatusForbidden();
			statusNotFound += crawledData.getStatusNotFound();
			size1 += crawledData.getSize1();
			size2 += crawledData.getSize2();
			size3 += crawledData.getSize3();
			size4 += crawledData.getSize4();
			size5 += crawledData.getSize5();
			typeText += crawledData.getTypeText();
			typeGif += crawledData.getTypeGif();
			typePng += crawledData.getTypePng();
			typePdf += crawledData.getTypePdf();
			typeJpeg += crawledData.getTypeJpeg();
			typeDoc += crawledData.getTypeDoc();
		 }
		 fetchFile.close();
		 visitedFile.close();
		 urlFile.close();
		 
		 uniqueURLInWebsite = uniqueURLInWebsiteSet.size();
		 uniqueURLOutsideWebsite = uniqueURLOutsideWebsiteSet.size();
		 uniqueURLOutsideUSC = uniqueURLOutsideUSCSet.size();
		 
		 report.append("Name: Harshith\n");
		 report.append("USC ID: XXX-XXX-XXX\n");
		 report.append("Site crawled: NBCNews.com\n");
		 
		 report.append("\nFetch Statistics\n");
		 report.append("================\n");
		 report.append("# fetches attempted: " + fetchesAttempted + "\n");
		 report.append("# fetches succeeded: " + fetchesSucceeded + "\n");
		 report.append("# fetches aborted: " + fetchesAborted + "\n");
		 report.append("# fetches failed: " + fetchesFailed + "\n");
		 
		 report.append("\nOutgoing URLs:\n");
		 report.append("================\n");
		 report.append("Total URLs extracted: " + totalUrls + "\n");
		 report.append("# unique URLs extracted: " + (uniqueURLInWebsite+uniqueURLOutsideWebsite) + "\n");
		 report.append("# unique URLs within Website: " + uniqueURLInWebsite + "\n");
		 report.append("# unique URLs outside Website: " + uniqueURLOutsideWebsite + "\n");
		 report.append("# unique URLs outside USC: " + uniqueURLOutsideUSC + "\n");
		 
		 report.append("\nStatus Codes:\n");
		 report.append("================\n");
		 report.append("200 OK: " + statusOK + "\n");
		 report.append("301 Moved Permanently: " + statusMoved + "\n");
		 report.append("401 Unauthorized: " + statusUnauth + "\n");
		 report.append("403 Forbidden: " + statusForbidden + "\n");
		 report.append("404 Not Found: " + statusNotFound + "\n");
		 
		 for(Integer key: MyCrawler.status.keySet()){
			 report.append(key + ": "+ MyCrawler.status.get(key));
			 report.append("\n");
			 
		 }
		 
		 report.append("\nFile Sizes:\n");
		 report.append("================\n");
		 report.append("< 1KB: " + size1 + "\n");
		 report.append("1KB ~ <10KB: " + size2 + "\n");
		 report.append("10KB ~ <100KB: " + size3 + "\n");
		 report.append("100KB ~ <1MB: " + size4 + "\n");
		 report.append(">= 1MB: " + size5 + "\n");
		 
		 report.append("\nContent Types:\n");
		 report.append("================\n");
		 for (String key : MyCrawler.contentType_counter.keySet()) {
			  report.append(key + ":  "+ MyCrawler.contentType_counter.get(key));
			  report.append("\n");}
		 report.append("text/html: " + typeText + "\n");
		 report.append("image/gif: " + typeGif + "\n");
		 report.append("image/jpeg: " + typeJpeg + "\n");
		 report.append("image/png: " + typePng + "\n");
		 report.append("application/pdf: " + typePdf + "\n");
		 report.append("doc: " + typeDoc + "\n");
		 report.close();
	}

	private static String getLocationString(Location location) {
		if(location == Location.OK){
			return "OK";
		}
		else if(location == Location.N_OK){
			return "N_OK";
		}
		return "outUSC";
	}
		
}
