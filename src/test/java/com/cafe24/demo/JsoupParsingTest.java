package com.cafe24.demo;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cafe24.demo.VO.Music;

import org.jsoup.Jsoup;
import org.jsoup.Jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/************
 * 
 * url을 입력받고 해당 url의 HTML을 파싱하는 라이브러리인 JSOUP 테스트 클래스 입니다.
 * 
 ***************/
@RunWith(SpringRunner.class)
@SpringBootTest
public class JsoupParsingTest {

	@Test
	public void contextLoads() {
	}

	@Test
	public void 블로그_메타태그_파싱_테스트() {
		Map<String, String> map = new HashMap<>();

		try {
			String url = "https://jihunhong.github.io";

			Document document = Jsoup.connect(url).get();

			Elements elments = document.select("meta");
			// HTML문서에서 meta태그 모두를 배열의 형태를 띤 Elements 담는다

			for (Element elment : elments) {
				map.put(elment.attr("property"), elment.attr("content"));
				// System.out.println(elment.attr("property") + " : " + elment.attr("content"));
				// :
				// : width=device-width, initial-scale=1
				// : Hugo 0.55.3 with theme Tranquilpeak 0.4.3-BETA
				// : JIHUN HONG
				// :
				// : harry's blog
				// og:description : harry's blog
				// og:type : blog
				// og:title : harry's blog
				// og:url : /
				// og:site_name : harry's blog
				// : summary
				// : harry's blog
				// : harry's blog
				// og:image : //www.gravatar.com/avatar/1195e5a46b27d50fe7234f2e57a8a24c?s=640
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertNotNull(map);

	}

	@Test
	public void 멜론_차트_파싱_테스트_1to100() {

		List<Music> music_info = new ArrayList<Music>();

		try {

			String url = "https://www.melon.com/chart/";

			Document document = Jsoup.connect(url).get();

			Elements music_titles = document.getElementsByClass("ellipsis rank01");
			// 노래 제목

			Elements artist_names = document.getElementsByClass("ellipsis rank02");
			// 가수 이름

			Elements album_names = document.getElementsByClass("ellipsis rank03");
			// 앨범 이름

			Elements album_imgs	 = document.getElementsByClass("image_typeAll");
			// 앨범 이미지

			for (int i = 0; i < 1; i++) {

				String title = music_titles.get(i).text();
				String artist = artist_names.get(i).getElementsByTag("span").text();
				String album = album_names.get(i).text();

				String img_url = album_imgs.get(i).select("img").attr("src").toString();

				Music music = new Music();
				music.setRank(i + 1);
				music.setTitle(title);
				music.setArtist(artist);
				music.setAlbum(album);
				music.setUrl(img_url);

				music_info.add(music);
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertNotNull(music_info);
	}

	@Test
	public void 지니뮤직_차트_파싱_테스트_1to50() {
		Map<String, String> music_info = new HashMap<>();

		try {

			String url = "https://genie.co.kr/chart/top200";

			Document document = Jsoup.connect(url).get();

			Elements music_titles = document.getElementsByClass("title ellipsis");
			// 노래 제목

			Elements artist_names = document.getElementsByClass("artist ellipsis");
			// 가수 이름

			for (int i = 0; i < 1; i++) {

				String title = music_titles.get(i).text();
				String artist = artist_names.get(i + 5).text();
				// 1위부터 5위까지 표시해주는 프리뷰가 있어서 5부터 시작해야함

				music_info.put(title, artist);
				System.out.println(title + " : " + artist);
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertNotNull(music_info);
	}

	@Test
	public void 벅스뮤직_차트_파싱_테스트_1_to100() {
		Map<String, String> music_info = new HashMap<>();

		try {

			String url = "https://music.bugs.co.kr/chart";

			Document document = Jsoup.connect(url).get();

			Elements music_titles = document.getElementsByClass("title");
			// 노래 제목

			Elements artist_names = document.getElementsByClass("artist");
			// 가수 이름

			for (int i = 0; i < 3; i++) {

				String title = music_titles.get(i + 3).text();
				String artist = artist_names.get(i + 1).select("p").select("a").get(0).text();

				music_info.put(title, artist);
				System.out.println(title + " : " + artist);
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertNotNull(music_info);
	}

}
