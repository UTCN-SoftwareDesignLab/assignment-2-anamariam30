import authHeader, { BASE_URL, HTTP } from "../http";
export default {
  allItems() {
    return HTTP.get(BASE_URL + "/emfo", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(book) {
    return HTTP.post(BASE_URL + "/emfo", book, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(book) {
    return HTTP.patch(BASE_URL + "/emfo", book, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },

  remove(id) {
    return HTTP.delete(BASE_URL + "/emfo/" + id, { headers: authHeader() });
  },
  search(keyword) {
    return HTTP.get(`https://www.googleapis.com/books/v1/volumes?q=intitle:${keyword}`)
      .then(response => {
        return response.data.items;
     
      })
  },
  generate(type){
     return HTTP.get(BASE_URL + "/emfo/export/"+type, { headers: authHeader() })
  },
  findMatch(keyword){
    return HTTP.get(BASE_URL + "/emfo/search/"+keyword, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );

  }


};
