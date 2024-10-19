$(document).ready(function () {
    const formEl = $('#form-submit');
    let updateId = null;

    formEl.on("click", event => {
        event.preventDefault();
        
        const name = $("#name").val();
        const email = $("#email").val();
        const dob = $("#dob").val().split('-').reverse().join('-');
        const address = $("#address").val();
        
        const data = {
            "name": name,
            "email": email,
            "dateOfBirth": dob,
            "address": address,
            "id": updateId
        }
        
        addOrUpdateData($('#form-submit').text(), data);
    });

    function addOrUpdateData(type = 'Add', data) {
        let url = 'http://localhost:8080/employees';
        let method = 'POST';
        if (type == 'Update') {
            url += "/" + data.id;
            method = 'PUT'; 
        } else {
            updateId = null;
        }

        $.ajax({
            url: url,
            type: method,
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (response) {
                console.log('Server Response:', response);
                alert("Success");
                getAllData();
                $('#form-submit').text('Add');
                updateId = null;
            },
            error: function(response) {
                if (response.status === 400)
                    alert("Please provide the values for all the fields");
            }
        });
        clearFields();
    }

    function clearFields() {
        $("#name").val('');
        $("#email").val('');
        $("#dob").val('');
        $("#address").val('');
    }


    function getAllData() {
        $.ajax({
            url: 'http://localhost:8080/employees',
            type: 'GET',
            success: function (response) {
                addDataToTable(response);
            }
        });
    }


    function updateRow(data) {
        console.log(data);
        $('#name').val(data.name);
        $('#email').val(data.email);
        const dob = data.dateOfBirth.split("-").reverse().join('-');
        $('#dob').val(dob);
        $('#address').val(data.address);
        $('#form-submit').text("Update");
        updateId = data.id;
    }

    function deleteRow(id) {
        $.ajax({
            url: 'http://localhost:8080/employees/' + id,
            type: 'DELETE',
            success: function (response) {
                console.log('Server Response:', response);
                alert("Success");
                getAllData();
            }
        });
        clearFields();
    }

    function addDataToTable(data) {
        const list = $("#list");
        list.empty();
        for (let i = 0; i < data.length; i++) {
            const element = data[i];
            const newRow = $('<tr>');
            newRow.append($("<td>").text(element.id));
            newRow.append($("<td>").text(element.name));
            newRow.append($("<td>").text(element.email));
            newRow.append($("<td>").text(element.dateOfBirth));
            newRow.append($("<td>").text(element.address));
            const updateId = element.id + "_update";
            const deleteId = element.id + "_delete";
            newRow.append($("<td>")
                .append($('<button>').text("UPDATE").attr('id', updateId))
                .append($('<button>').text("DELETE").attr('id', deleteId)))
            list.append(newRow);

            $('#'+updateId).on('click', () => updateRow(element))

            $('#'+deleteId).on('click', () => deleteRow(element.id))
        }
    }

    getAllData();
})


