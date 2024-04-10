<script>
    import Calendar from '@event-calendar/core';
    import TimeGrid from '@event-calendar/time-grid';
    import '@event-calendar/core/index.css';

    let plugins = [TimeGrid];
    let options = {
        view: 'timeGridWeek',
        events: [
            // your list of events
        ]
    };
</script>

<div class="calendar-container">
    <Calendar {plugins} {options} />
</div>

<style>
    :root {
        --ec-text-color: #333; /* Text color */
        --ec-bg-color: oklch(95.1276% 0.007445 260.731539 /1); /* Background color */
        --ec-border-color: #ddd; /* Border color */
        --ec-accent-color: #007bff; /* Accent color for elements like buttons or active states */
        --ec-button-bg-color: #f8f9fa; /* Button background color */
        --ec-button-border-color: #ddd; /* Button border color */
        --ec-button-text-color: #333; /* Button text color */
        --ec-button-active-bg-color: #007bff; /* Button background color when active/hovered */
        --ec-button-active-border-color: #0056b3; /* Button border color when active/hovered */
        --ec-button-active-text-color: #fff; /* Button text color when active/hovered */
        --ec-today-bg-color: #eaf3ff; /* Background color for today's date */
        --ec-highlight-color: #ffff88; /* Background color for highlighted elements */
        --ec-event-text-color: #333; /* Text color for events */
        --ec-event-bg-color: #a991f7; /* Background color for events */
        --ec-hs: 210, 50; /* Hue-saturation for shadows (used in box-shadow HSLA) */
        --ec-now-indicator-color: #ff0000; /* Color for the current time indicator */
        --ec-popup-bg-color: #f9f9f9; /* Background color for popups */
        --ec-list-day-bg-color: #f0f0f0; /* Background color for list days */
        --ec-bg-event-color: #f0f0f0; /* Background color for background events */
        --ec-bg-event-opacity: 0.5; /* Opacity for background events */
    }

    .calendar-container {
        max-height: 90%; /* Adjust the height as needed */
        overflow-y: scroll; /* This will add a scrollbar when the content overflows */
        border-radius: 1rem;
    }

    /* Ensure internal elements are set to 100% width for responsive scaling */
    :global(.ec) {
        width: 100%;
    }

    /* ... your existing styles ... */
</style>
