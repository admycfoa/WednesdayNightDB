package project.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import project.model.DataTableModel;
import project.model.SearchInputDataModel;

@Controller
public class HomeController
{
	private ArrayList<DataTableModel> data;
	
	private int resultCount = 0;
	
	@RequestMapping("")
	public String Home(Model model)
	{
		try
		{
			BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/data/data.csv"));
			String dataLine;
			String[] dataArray;
			this.data = new ArrayList<DataTableModel>();
			
			while((dataLine = bufferedReader.readLine()) != null)
			{
				dataArray = dataLine.split(",");
				
				this.data.add(new DataTableModel
				(
					dataArray[0],
					dataArray[1],
					dataArray[2],
					dataArray[3],
					dataArray[4],
					Integer.parseInt(dataArray[5]),
					dataArray[6],
					dataArray[7]
				));
			}
			
			bufferedReader.close();
		}
		catch (IOException IOException)
		{
			System.out.println("ไม่พบฐานข้อมูล");
		}
		
		model.addAttribute("season", this.data.get(this.data.size() - 1).getSeason());
		model.addAttribute("nightnumber", this.data.get(this.data.size() - 1).getNightnumber());
		
		return "home";
	}
	
	@ResponseBody
	@RequestMapping(value = "/background", produces = MediaType.IMAGE_JPEG_VALUE)
	public void Background(HttpServletResponse HttpServletResponse) throws IOException
	{
		ClassPathResource ClassPathResource = new ClassPathResource("images/Background.jpg");
				
		HttpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(ClassPathResource.getInputStream(), HttpServletResponse.getOutputStream());
	}
	
	@ResponseBody
	@PostMapping("/season")
	public ArrayList<String> Season()
	{
		ArrayList<String> season = new ArrayList<String>();
		
		for(DataTableModel row : this.data)
		{
			if(!season.contains(row.getSeason()))
			{
				season.add(row.getSeason());
			}
		}
		
		return season;
	}
	
	@ResponseBody
	@PostMapping("/storytellername")
	public ArrayList<String> Storytellername()
	{
		ArrayList<String> storytellername = new ArrayList<String>();
		
		for(DataTableModel row : this.data)
		{
			if(!storytellername.contains(row.getStorytellername()))
			{
				storytellername.add(row.getStorytellername());
			}
		}
		
		return storytellername;
	}
	
	@ResponseBody
	@PostMapping("/search")
	public ArrayList<DataTableModel> Search
	(
		@RequestBody SearchInputDataModel SearchInputDataModel,
		Model model
	)
	{
		ArrayList<DataTableModel> result = new ArrayList<>(this.data);
		String[] keywords = SearchInputDataModel.getKeyword().split(" ");

		for(int resultIndex = 0 ; resultIndex < result.size(); ++resultIndex)
		{
			if(
				!SearchInputDataModel.getSeason().equals("") &&
				!result.get(resultIndex).getSeason().equals(SearchInputDataModel.getSeason())
			)
			{
				result.remove(resultIndex);
				--resultIndex;
			}
			else if
			(
				!SearchInputDataModel.getNightnumber().equals("") &&
				!result.get(resultIndex).getNightnumber().equals(SearchInputDataModel.getNightnumber())
			)
			{
				result.remove(resultIndex);
				--resultIndex;
			}
			else if
			(
				!SearchInputDataModel.getStoryname().equals("") &&
				!result.get(resultIndex).getStoryname().contains(SearchInputDataModel.getStoryname())
			)
			{
				result.remove(resultIndex);
				--resultIndex;
			}
			else if
			(
				!SearchInputDataModel.getStorytellername().equals("") &&
				!result.get(resultIndex).getStorytellername().equals(SearchInputDataModel.getStorytellername())
			)
			{
				result.remove(resultIndex);
				--resultIndex;
			}
			else if
			(
				!keywords[0].equals("") &&
				!result.get(resultIndex).getKeyword().contains(keywords[0])
			)
			{
				result.remove(resultIndex);
				--resultIndex;
			}
			else if(!SearchInputDataModel.getStorylength().equals("2"))
			{
				if
				(
					SearchInputDataModel.getStorylength().equals("1") &&
					result.get(resultIndex).getStorylength() > 899
				)
				{
					result.remove(resultIndex);
					--resultIndex;
				}
				else if
				(
					SearchInputDataModel.getStorylength().equals("0") &&
					result.get(resultIndex).getStorylength() < 900
				)
				{
					result.remove(resultIndex);
					--resultIndex;
				}
				else
				{
					if
					(
						!SearchInputDataModel.getHadpicture().equals("2") &&
						!result.get(resultIndex).getHadpicture().equals(SearchInputDataModel.getHadpicture())
					)
					{
						result.remove(resultIndex);
						--resultIndex;
					}
				}
			}
			else if
			(
				!SearchInputDataModel.getHadpicture().equals("2") &&
				!result.get(resultIndex).getHadpicture().equals(SearchInputDataModel.getHadpicture())
			)
			{
				result.remove(resultIndex);
				--resultIndex;
			}
		}
		
		for(int keywordsIndex = 1; keywordsIndex < keywords.length; ++keywordsIndex)
		{
			for(int resultIndex = 0 ; resultIndex < result.size(); ++resultIndex)
			{
				if(!result.get(resultIndex).getKeyword().contains(keywords[keywordsIndex]))
				{
					result.remove(resultIndex);
					--resultIndex;
				}
			}
		}
		
		this.resultCount = result.size();
		
		return result;
	}
	
	@ResponseBody
	@PostMapping("/resultcnt")
	public int ResultCount()
	{
		return this.resultCount;
	}
}