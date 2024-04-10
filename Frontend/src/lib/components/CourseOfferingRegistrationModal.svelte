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

    let paymentOptions = [];

    function mutexPayments(event) {
        paymentOptions.forEach((checkbox) => {
            if (checkbox !== event.target) {
                checkbox.checked = false;
            }
        });
        console.log(paymentOptions);
    }

    async function registerForCourseOffering() {
        console.log("u clickd this!!!");
        let selectedPaymentOption = paymentOptions.find((paymentOption) => paymentOption.checked);
        if (selectedPaymentOption) {
            try {
                const registerForCourseOffering = await axios.post('http://localhost:8080/registrations/create', {
                    courseOfferingId: selectedCourseOffering.id,
                    paymentInfoId: selectedPaymentOption.value,
                    pricePaid: 69,
                    registrationDate: new Date().toISOString()
                }, {
                    headers: {
                        userToken: 'token'
                    }
                });
                console.log(registerForCourseOffering.data);
            } catch (error) {
                console.error(error);
            }
        }
    }

</script>

<dialog bind:this={dialog} class="modal">
    <div class="flex-col w-11/12 max-w-5xl modal-box h-2/3 bg-base-100">
        <div class="text-lg font-bold">Registering for Course Offering</div>
            <div class="grid grid-cols-3 pt-2 gap-x-2 h-5/6">
                <div class="p-2 rounded-box bg-base-200">
                    <strong>Course Type:</strong> <span>{selectedCourseOffering.daysOffered}</span> <br>
                    <strong>Price:</strong> <span>{selectedCourseOffering.daysOffered}</span> <br>
                    <strong>Start Date:</strong> <span>{selectedCourseOffering.startDate}</span> <br>
                    <strong>End Date:</strong> <span>{selectedCourseOffering.endDate}</span> <br>
                    <strong>Days Offered:</strong> <span>{selectedCourseOffering.daysOffered}</span> <br>
                    <strong>Room:</strong> <span>{selectedCourseOffering.daysOffered}</span>
                </div>
                <div class="overflow-auto rounded-box bg-base-200">
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
                <div class = "overflow-auto rounded-box bg-base-200">
                    <div class="overflow-auto">
                        <table class = "table">
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>Card Number</th>
                                    <th>Expiration Date</th>
                                </tr>
                            </thead>
                            <tbody>
                                {#each paymentInfos as paymentInfo, index}
                                    <tr>
                                        <td><input type="checkbox" class="checkbox" bind:this={paymentOptions[index]} bind:value={paymentInfo.id} on:click={mutexPayments}></td>
                                        <td>{paymentInfo.cardNumber}</td>
                                        <td>{paymentInfo.expirationYear}/{paymentInfo.expirationMonth}</td>
                                    </tr>
                                {/each}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="modal-action gap-x-4">
                <form method="dialog">
                    <!-- if there is a button, it will close the modal -->
                    <button class="btn">Cancel</button>
                    <button class="btn" on:click|preventDefault={registerForCourseOffering}>Register</button>
                </form>
            </div>
        </div>
</dialog>