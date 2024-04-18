<template>
    <div class="all"> 
    <Header></Header>
    <div class="wrapper">
            <login></login>
    </div>
    <Footer></Footer>
    </div>
</template>

<script setup>
    import login from '../components/Login/Main.vue';
    import Header from "@/components/Header/Header.vue";
    import Footer from "@/components/Footer/Footer.vue";
</script>

<style scoped>
.wrapper {
    margin-left:12.5%;
    margin-right:12.5%;
    width:75%;
    display: grid;
}
.all {
        display: grid;
        grid-template-rows: 100px minmax(780px, auto) 200px;
        align-items: center;
    }

</style>