<template>
  <div>
    <Dashboard>
      <div class="main-content">
        <div class="button-container">
          <button @click="showCreateClassTypePopup = true">Create/Propose Class Type</button>
        </div>
        <classtypetable :class-types="classTypes" @edit-classType="editClassType" @delete-classType="deleteClassType" @approve-classType="approveClassType" />
      </div>
    </Dashboard>
    <div class="modal-overlay" v-if="showCreateClassTypePopup">
      <createclasstypes class ="modal-content" @close="showCreateClassTypePopup = false" @create-classType="addClassType" />
    </div>
    <div class="modal-overlay" v-if="showEditClassTypePopup">
      <editclasstype :classTypeId="chosenClassType" class ="modal-content" @close="showEditClassTypePopup = false"  @edit-classType="editClassType"/>
    </div>
  </div>
</template>

<script>
import Dashboard from '@/pages/Dashboard'
import createclasstypes from '@/components/createclasstypes'
import classtypetable from '@/components/classtypetable'
import editclasstype from '@/components/editclasstype'
import axios from 'axios'
import {showErrMsg} from "../../components/loginform.vue";

export default {
  name: 'classTypePage',
  components: {
    Dashboard,
    classtypetable,
    createclasstypes,
    editclasstype
  },
  data() {
    return {
      errorMessage: "",
      showCreateClassTypePopup: false,
      showEditClassTypePopup: false,
      chosenClassType: null,
      classTypes: [
        this.loadClassTypes()
      ],
    };
  },
  mounted() {
    this.loadClassTypes();
    this.fetchClassType(this.classTypeId);
  },
  methods: {
    fetchClassType(id) {
      axios.get(`http://localhost:8080/classtypes/${id}`)
        .then(res => {
          this.classType = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    addClassType(classType) {
      this.loadClassTypes();
      this.showCreateClassTypePopup = false;
    },
    editClassType(index) {
      this.chosenClassType = index;
      this.showEditClassTypePopup = true;
      this.loadClassTypes();
    },
    deleteClassType(index) {
      this.loadClassTypes();
    },
    loadClassTypes() {
      axios.get('http://localhost:8080/classtypes')
        .then(res => {
          this.classTypes = res.data;
        })
        .catch(err => {
          console.error(err);
        });
    },
    approveClassType(id) {
      axios.put(`http://localhost:8080/classtypes/${id}/approve`)
        .then(res => {
          showErrMsg.call(this, "Class type approved");
          this.loadClassTypes()
        }).catch(err => {
        showErrMsg.call(this, err.response.data);
      });
      this.loadClassTypes();
    },
  }
};
</script>

<style scoped>


.button-container {
  display: flex;
  justify-content: flex-end;
  margin-right: 24px;
  margin-top: 24px;

}

button {
  background-color: #77DD77;
  color: white;
  border-radius: 4px;
  width: 256px;
  height: 48px;
  border: none;
  font-weight: bold;
}

button:hover {
  background-color: #2aaa2a;
}


.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5); /* Semi-transparent black overlay */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999; /* Ensure the modal is on top of other content */
}

/* Styles for the modal content */
.modal-content {
  padding: 20px;
  border-radius: 8px;
  min-width: 400px;
  max-width: 700px;
  max-height: 800px;
  height: auto;
  overflow-y: auto; /* Enable scrolling if the content exceeds the height */
  position: absolute;
  top: 50;
  left: 35%;
}

/* Close button styles */
.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  cursor: pointer;
}
</style>
