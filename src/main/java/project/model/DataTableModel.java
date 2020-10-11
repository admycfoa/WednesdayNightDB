package project.model;

public class DataTableModel
{
	private String season;
	private String nightnumber;
	private String storyname;
	private String storytellername;
	private String keyword;
	private int storylength;
	private String hadpicture;
	private String youtubelink;
	
	public String getSeason()
	{
		return season;
	}
	public void setSeason(String season)
	{
		this.season = season;
	}
	public String getNightnumber()
	{
		return nightnumber;
	}
	public void setNightnumber(String nightnumber)
	{
		this.nightnumber = nightnumber;
	}
	public String getStoryname()
	{
		return storyname;
	}
	public void setStoryname(String storyname)
	{
		this.storyname = storyname;
	}
	public String getStorytellername()
	{
		return storytellername;
	}
	public void setStorytellername(String storytellername)
	{
		this.storytellername = storytellername;
	}
	public String getKeyword()
	{
		return keyword;
	}
	public void setKeyword(String keyword)
	{
		this.keyword = keyword;
	}
	public int getStorylength()
	{
		return storylength;
	}
	public void setStorylength(int storylength)
	{
		this.storylength = storylength;
	}
	public String getHadpicture()
	{
		return hadpicture;
	}
	public void setHadpicture(String hadpicture)
	{
		this.hadpicture = hadpicture;
	}
	public String getYoutubelink()
	{
		return youtubelink;
	}
	public void setYoutubelink(String youtubelink)
	{
		this.youtubelink = youtubelink;
	}
	
	public DataTableModel()
	{
		super();
	}
	
	public DataTableModel
	(
		String season,
		String nightnumber,
		String storyname,
		String storytellername,
		String keyword,
		int storylength,
		String hadpicture,
		String youtubelink
	)
	{
		super();
		
		this.season = season;
		this.nightnumber = nightnumber;
		this.storyname = storyname;
		this.storytellername = storytellername;
		this.keyword = keyword;
		this.storylength = storylength;
		this.hadpicture = hadpicture;
		this.youtubelink = youtubelink;
	}
	
	@Override
	public String toString()
	{
		return " ซีซั่น = " + this.season +
			           " คืนที่ = " + this.nightnumber +
			           "   ชื่อเรื่อง = " + this.storyname +
			           "   ชื่อผู้เล่า = " + this.storytellername +
			           "   คำสำคัญ = " + this.keyword +
			           "   ความยาว = " + this.storylength +
			           "   มีรูป = " + this.hadpicture +
			           "   ลิงค์ยูทูป = " + this.youtubelink;
	}
}