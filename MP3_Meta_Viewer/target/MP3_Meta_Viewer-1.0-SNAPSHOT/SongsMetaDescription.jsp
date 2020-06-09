<%@ page
	import="java.util.Collection, com.sheifu.mp3_meta_viewer.Entities.Song "
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>MP3 Files Description</title>
		<link href="main.css" rel="stylesheet">
	</head>
	<body>
		<div class="container" >
			<h2>Describing Your Mp3 Files</h2>
			<br><br><br>
			<table>
				<tr>
					<th class = "col-title" >Title</th>
					<th class = "col-artist">Artist</th>
					<th class = "col-year">Year</th>
					<th class = "col-album">Album</th>
				</tr>
				<% 
					Collection<Song> songs = (Collection<Song>)request.getAttribute("songs");
					for (Song song: songs) {
				%>
				<tr>
					<td><%= song.getTitle() %></td>
					<td><%= song.getArtist() %></td>
					<td><%= song.getYear() %></td>
					<td><%= song.getAlbum() %></td>
				</tr>
				<%
					}
				%>
			</table>
		</div>
		
		
	</body>
</html>