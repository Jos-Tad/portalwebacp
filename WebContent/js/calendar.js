document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        selectable: true,
        lang: 'es',
        dayMaxEvents: true,
        headerToolbar: {
            left: 'prev, next, today',
            center: 'title',
            right: 'dayGridMonth, timeGridWeek, timeGridDay'
        },
        events: [
            {
                title: 'Inauguración del arboreto',
                start: '2021-04-02',
                end: '2021-04-03T23:00:00'
            },
            {
                title: 'Conferencia acerca de los árboles',
                start: '2021-04-05T09:00:00'
            },
            {
                title: 'Conferencia acerca de los árboles',
                start: '2021-04-05T11:00:00'
            },
            {
                title: 'Conferencia acerca de los árboles',
                start: '2021-04-05T13:00:00'
            },
            {
                title: 'Conferencia acerca de los árboles',
                start: '2021-04-05T15:00:00'
            },
            {
                title: 'Venta de semillas especiales',
                start: '2021-04-10'
            },
            {
                title: 'Work in Google Meet',
                start: '2021-04-19T10:00:00',
                end: '2021-04-20T23:00:00',
                url: 'https://meet.google.com'
            },
            {
                title: 'Conferencia acerca de los Guillermo',
                start: '2021-04-04T09:00:00'
            }
            
        ]
    });
    calendar.render();
});