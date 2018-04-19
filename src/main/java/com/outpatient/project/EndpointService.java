package com.outpatient.project;
 

import java.io.ByteArrayOutputStream;
import java.io.InputStream;


import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/physicaltherapy") 
public class EndpointService {
	
	@GET
	@Path("/default")
	public Response getMsg(@QueryParam("location") String location) {
		if(location.length() == 0)
			return Response.status(Status.BAD_REQUEST).entity(Constant.EMPTY_LOCATION).build();
		
		String url = Constant.URL+location+"&offset=";
		String output = recursiveGeneralRequest(0, url, new double[4]);
		if(output.length() == 0)
			return Response.status(Status.BAD_REQUEST).entity(Constant.ERROR_MSG).build();
		return Response.status(Status.OK).entity(output).build();
	}
	
	@GET
	@Path("/sorted")
	public Response getSortedMsg(@QueryParam("location") String location) {
		if(location.length() == 0)
			return Response.status(Status.BAD_REQUEST).entity(Constant.EMPTY_LOCATION).build();
 
		String url = Constant.URL+location+"&offset=";
		List<Business> businessList = recursiveSortedRequest(0, url, new LinkedList<Business>());
		
		Collections.sort(businessList, new Comparator<Business>() {
			   @Override
			   public int compare(Business a, Business b) {
				   return b.getRating() - a.getRating();
			   }
		});
		
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(out, businessList);
			final byte[] data = out.toByteArray();
			System.out.println(new String(data));
			return Response.status(200).entity(new String(data)).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(Constant.ERROR_MSG).build();
		}
	}
	
	private String recursiveGeneralRequest(int offset, String url, double[] result){
		
		url += offset;
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		request.addHeader(Constant.AUTHORIZATION, Constant.TOKEN);
		
		HttpResponse response;
		try {
			response = client.execute(request);
			System.out.println("Response Code : " 
	                + response.getStatusLine().getStatusCode());
			InputStream in = response.getEntity().getContent();
		
			ObjectMapper objectMapper = new ObjectMapper();

			//read JSON like DOM Parser
			JsonNode rootNode = objectMapper.readTree(in);
			int total = rootNode.path(Constant.TOTAL).asInt();
			
			new ResponseService().getSummary(rootNode, result);
			int limit = 50;
			if(total - offset >= limit){
				return recursiveGeneralRequest(offset+limit, url, result);
				
			} else {
				String output = "{\"numberofpoints\":"+result[0]+",\"pointswithrating\":"+result[1]
					+",\"averagerating\":"+result[2]+",\"totalreviews\":"+result[3]+"}";
			
			    System.out.println(output);
			    return output;
			}

		}  catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";	
			
	}
	
	
    private List<Business> recursiveSortedRequest(int offset, String url, List<Business> businessList){
		
		url += offset;
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		request.addHeader(Constant.AUTHORIZATION, Constant.TOKEN);
		
		HttpResponse response;
		try {
			response = client.execute(request);
			System.out.println("Response Code : " 
	                + response.getStatusLine().getStatusCode());
			InputStream in = response.getEntity().getContent();
		
			ObjectMapper objectMapper = new ObjectMapper();

			//read JSON like DOM Parser
			JsonNode rootNode = objectMapper.readTree(in);
			int total = rootNode.path(Constant.TOTAL).asInt();
			
			new ResponseService().getSortedSummary(rootNode, businessList);
			int limit = 50;
			if(total - offset >= limit){
				return recursiveSortedRequest(offset+limit, url, businessList);	
			} else {
			    return businessList;
			}

		}  catch (Exception e) {
			e.printStackTrace();
		}
		
		return businessList;	
			
	}



	
//	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException  {
//		String location = "atlanta";
//		String url = "https://api.yelp.com/v3/businesses/search?categories=physicaltherapy&limit=50&location="+location+"&offset=";
//		//new EndpointService().recursiveGeneralRequest(0, url, new double[4]);
//		//new EndpointService().recursiveSortedRequest(0, url, new LinkedList<Business>());
//		
//		List<Business> businessList = new EndpointService().recursiveSortedRequest(0, url, new LinkedList<Business>());
//		
//		Collections.sort(businessList, new Comparator<Business>() {
//			   @Override
//			   public int compare(Business a, Business b) {
//				   return b.getRating() - a.getRating();
//			   }
//		});
//			
//			
//		
//		final ByteArrayOutputStream out = new ByteArrayOutputStream();
//		final ObjectMapper mapper = new ObjectMapper();
//
//		mapper.writeValue(out, businessList);
//
//		final byte[] data = out.toByteArray();
//		System.out.println(new String(data));
//		
//		/*try {
//			byte[] jsonData = Files.readAllBytes(Paths.get("emp.txt"));
//
//			
//			ObjectMapper objectMapper = new ObjectMapper();
//
//			//read JSON like DOM Parser
//			JsonNode rootNode = objectMapper.readTree(new ByteArrayInputStream(jsonData));
//			
//			double[] result  = new double[4];
//			new ResponseService().getSummary(rootNode, result);
//			
//			
//				String output = "{\"numberofpoints\":"+result[0]+",\"pointswithrating\":"+result[1]
//					+",\"averagerating\":"+result[2]+",\"totalreviews\":"+result[3]+"}";
//			
//			    System.out.println(output);
//			  
//			
//			//new ResponseService().getSortedSummary(new ByteArrayInputStream(jsonData));
//			//new ResponseService().getSummary(new ByteArrayInputStream(jsonData));
//			
//			
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//*/
//	}
 
}