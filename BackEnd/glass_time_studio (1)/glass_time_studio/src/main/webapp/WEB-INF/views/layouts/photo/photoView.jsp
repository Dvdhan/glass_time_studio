<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Photo Upload</title>
    <link rel="stylesheet" href="../../css/background_black.css">


    <style>
        fieldset{
            width: 30em;
            margin: auto;
            text-align: center;
        }

    </style>
</head>
<body>
    <%@ include file="../../navigation.jsp" %>
    <form id="uploadForm" method="post" enctype="multipart/form-data" action="/cloud/photo/upload">
    <fieldset>
        <input type="file" name="file"/>
        <button type="submit">Upload</button>
    </fieldset>
    </form>
    <img id="uploadedPhoto" src="" alt="Uploaded Photo"/>

    <script>
        document.querySelector('form').addEventListener('submit', async function(event) {
            event.preventDefault();
            const formData = new FormData(event.target);
            const response = await fetch('/cloud/photo/upload', {
                method: 'POST',
                body: formData
            });
            const url = await response.text();
            document.getElementById('uploadedPhoto').src = url;
        });
    </script>
</body>
</html>