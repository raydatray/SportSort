import { writable } from 'svelte/store';

export let userRole = writable('test');
export let userToken = writable('test');