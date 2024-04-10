<script>
    import { onMount, afterUpdate } from 'svelte';
    import axios from 'axios';
    export let selectedCourseOffering;
    export let associatedCourseSessions;

    let dialog;

    let paymentInfos = [];

    onMount(async () => {
        try{
            const getPaymentInfos = await axios.get('http://localhost:8080/paymentInfo/getAll', {
                headers: {
                    userToken: 'token'
                }
            });
            paymentInfos = getPaymentInfos.data;
        } catch (error) {
            console.error(error);
        }
    });

    afterUpdate(() => {
        if (selectedCourseOffering) {
            dialog.showModal();
        }
    });
</script>

<dialog bind:this={dialog} class="modal">
    <div class="modal-box w-11/12 max-w-5xl h-2/3 bg-base-100 flex-col">
        <div class="font-bold text-lg">Registering for Course Offering</div>
            <div class="grid grid-cols-3 pt-2 gap-x-2 h-5/6">
                <div class="rounded-box bg-base-200 p-2">
                    <strong>Course Type:</strong> <span>{selectedCourseOffering.daysOffered}</span> <br>
                    <strong>Price:</strong> <span>{selectedCourseOffering.daysOffered}</span> <br>
                    <strong>Start Date:</strong> <span>{selectedCourseOffering.startDate}</span> <br>
                    <strong>End Date:</strong> <span>{selectedCourseOffering.endDate}</span> <br>
                    <strong>Days Offered:</strong> <span>{selectedCourseOffering.daysOffered}</span> <br>
                    <strong>Room:</strong> <span>{selectedCourseOffering.daysOffered}</span>
                </div>
                <div class="rounded-box overflow-auto bg-base-200">
                    <div class="overflow-auto">
                        <table class = "table">
                            <thead>
                                <tr>
                                    <th>Session #</th>
                                    <th>Date</th>
                                    <th>Start Time</th>
                                    <th>End Time</th>
                                </tr>
                            </thead>
                            <tbody>
                                {#each associatedCourseSessions as session, index}
                                    <tr>
                                        <td>{index + 1}</td>
                                        <td>{session.date}</td>
                                        <td>{session.startTime}</td>
                                        <td>{session.endTime}</td>
                                    </tr>
                                {/each}
                            </tbody>
                        </table>
                    </div>
                </div>
                <div>
                    {#each paymentInfos as paymentInfo}
                        <div class="rounded-box bg-base-200">
                            <strong>Card Number:</strong> <span>{paymentInfo.cardNumber}</span> <br>
                            <strong>Card Type:</strong> <span>{paymentInfo.paymentType}</span> <br>
                            <strong>Expiration Date:</strong> <span>{paymentInfo.expirationYear}/{paymentInfo.expirationMonth}</span> <br>
                        </div>
                    {/each}
                </div>
            </div>
            <div class="modal-action">
                <form method="dialog">
                    <!-- if there is a button, it will close the modal -->
                    <button class="btn">Close</button>
                </form>
            </div>
        </div>
</dialog>