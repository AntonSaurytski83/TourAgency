function check(formID) {
    var pattern = null;

    switch(formID){
        case 'name':
            pattern =  new RegExp('(^[A-Za-z0-9\\s]{3,30}$)|(^[А-ЯЁёа-я0-9\\s]{3,30}$)', '');
            break;
        case 'details':
            pattern =  new RegExp('(^[a-zA-Zа-яЁёА-Я0-9\\*!\\?,\\.«»\\-:"\\()\\^\\s]{6,1200}$)', '');
            break;


    }

    inputStr = document.getElementById(formID + 'Form').value;

    if(pattern.test(inputStr)) {
        document.getElementById(formID).style.color = 'green';
    } else {
        document.getElementById(formID).style.color = 'red';
    }
}


