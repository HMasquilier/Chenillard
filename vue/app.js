const app = Vue.createApp({
    data() {
        return {
            chenillardState: false
        }
    },
    methods: {
        toogleChenillard() {
            this.chenillardState = !this.chenillardState
        }
    }
})

app.mount('#app')