<%@page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sending email</title>
    <script type="text/javascript" src="jquery-1.2.6.min.js"></script>
</head>
<body>
<h1>Send an email</h1>

<form action="sendMail" method="post" enctype="multipart/form-data">
    <input type="text" placeholder="Token" name="token"/>
    <br>
    <input type="text" placeholder="To" name="to"/>
    <br>
    <input type="text" placeholder="From" name="from"/>
    <br>
    <input type="text" placeholder="Title" name="title"/>
    <br/>
    <textarea cols="40" rows="5" type="text" name="content" placeholder="Message"></textarea>
    <br/>

    <div id="files">

        <div id="file1" class="row">
            <input type="file" name="presoFile1">
        </div>

    </div>


    <p>
        <a id="add-file-upload">Add File Upload</a>
    </p>

    <input type="submit"/>


</form>


<script lang="javascript">

    $(
            function () {
                // Get the add new upload link.
                var jAddNewUpload = $("#add-file-upload");

                // Hook up the click event.
                jAddNewUpload
                        .attr("href", "javascript:void( 0 )")
                        .click(
                        function (objEvent) {
                            AddNewUpload();

                            // Prevent the default action.
                            objEvent.preventDefault();
                            return ( false );
                        }
                )
                ;

            }
    )


    function AddNewUpload() {
        var jFilesContainer = $("#files");
        var intNewFileCount = (jFilesContainer.find("div.row").length + 1);

        if (intNewFileCount > 10) return false;
        var jUpload = '<div data-role="fieldcontain" class="row">' +
                '<label for="upload_file' + intNewFileCount + '"></label><input type="file" name="upload_file' + intNewFileCount + '" id="upload_file' + intNewFileCount + '" value="" />' +
                '</div>';
        jFilesContainer.append(jUpload);
    } // end function

</script>
</body>
</html>