<script>
    import { onMount } from 'svelte'
    import axios from 'axios';

    /**
     * Array containing course offering objects.
     * @type {Array<{
     *   courseOfferingId: string,
     *   paymentInfo: string,
     *   pricePaid: string,
     *   registrationDate: string
     * }>}
     */
    let registrations = [];

    let paymentInfos = [];

    let courseOfferings = [];

    let courseTypes = [];

    /**
     * @typedef {Object} Registration
     * @property {string} courseOfferingId
     * @property {string} paymentInfo
     * @property {string} pricePaid
     * @property {string} registrationDate
     */

    const backendUrl = 'http://127.0.0.1:8080/';

    const AXIOS = axios.create({
        baseURL: backendUrl,
        headers: { 'Access-Control-Allow-Origin': 'http://localhost:5173/' }
    });

    onMount(async () => {
        try {
            // Fetch all registrations
            const response = await AXIOS.get('/registrations/getByCustomer', {
                headers: {
                    'userToken': sessionStorage.getItem('token')
                }
            });
            registrations = response.data;

            // Fetch all payment infos
            const response2 = await AXIOS.get('paymentInfo/getAll', {
                headers: {
                    'userToken': sessionStorage.getItem('token')
                }
            });
            paymentInfos = response2.data;

            const response3 = await AXIOS.get('courseOfferings/getAll', {
                headers: {
                    'userToken': sessionStorage.getItem('token')
                }
            });
            courseOfferings = response3.data;

            const response4 = await AXIOS.get('courseTypes/getAllApproved');
            courseTypes = response4.data;

            registrations.forEach(registration => {
                const paymentInfo = paymentInfos.find(p => p.cardNumber === registration.paymentInfo);
                if (paymentInfo) {
                    registration.paymentInfo = paymentInfo.paymentType + " ending in " + registration.paymentInfo;
                }
                registration.pricePaid += ".00";

                const courseOffering = courseOfferings.find(c => c.id === registration.courseOfferingId)
                if (courseOffering) {
                    const courseType = courseTypes.find(t => t.id === courseOffering.courseTypeId);
                    if (courseType) {
                        registration.courseName = courseType.courseName;
                        registration.startDate = courseOffering.startDate;
                        registration.endDate = courseOffering.endDate;
                    }
                }
            })

            registrations = registrations;
        } catch (error) {
            console.error('Failed to fetch registrations:', error);
        }
    });
</script>

<h1 class="text-2xl font-bold p-2">Registrations</h1>
<div class="content">
    <div class="listAll p-2">
        <div class="overflow-x-auto">
            <table class="table">
                <thead>
                <tr>
                    <th>Registration Date</th>
                    <th>Amount Paid</th>
                    <th>Course Name</th>
                    <th>Course Offering Start Date</th>
                    <th>Course Offering End Date</th>
                    <th>Payment Information</th>
                </tr>
                </thead>
                <tbody>
                {#each registrations as registration}
                    <tr class="hover">
                        <th class="font-normal">{registration.registrationDate}</th>
                        <th class="font-normal">{registration.pricePaid}</th>
                        <th class="font-normal">{registration.courseName}</th>
                        <th class="font-normal">{registration.startDate}</th>
                        <th class="font-normal">{registration.endDate}</th>
                        <th class="font-normal">{registration.paymentInfo}</th>
                    </tr>
                {/each}
                </tbody>
            </table>
        </div>
    </div>
</div>

<style>

</style>