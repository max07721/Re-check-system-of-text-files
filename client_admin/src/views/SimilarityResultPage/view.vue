<template>
  <div>
    <el-row>
      <el-col :span="24" style="display: flex; justify-content: center; padding-top: 2%;">
        <div>
          <p>实验内容: {{experimentalTopic}}</p>
          <p>作者: {{ author }}</p>
          <p>指导老师: {{ teacherName }}</p>
          <p>报告日期: {{ reportTime }}</p>
          <p>报告编号: {{ experimentalReportId }}</p>
          <el-button @click="getList" :disabled="buttonDisable">查看详细报告</el-button>
        </div>
        <div style="padding-left: 5rem; display: flex; justify-content: center; align-items: center;">
          <div style="background-color: white; border-radius: 50%; display: flex; justify-content: center; align-items: center;">
            <el-progress type="circle" :percentage="similarity"></el-progress>
          </div>
        </div>
      </el-col>
      <el-col :span="17" style="padding-left: 5%;padding-right: 4%; padding-top: 5%;">
        <!-- <div v-html="similarityResult"></div> -->
        <div v-if="originArticle != ''" style="box-shadow: 0 4px 8px rgba(0, 0, 0, .16), 0 2px 12px rgba(0, 0, 0, .08); padding: 3rem;">
          <span style="font-weight: bold;">
            Your Answer:
          </span>
          <div v-html="originArticle"></div>
        </div>
      </el-col>
      <el-col :span="6" style="padding-top: 5%; padding-right: 1%; padding-left: 1%;">
        <div v-if="originArticle != ''" style="box-shadow: 0 4px 8px rgba(0, 0, 0, .16), 0 2px 12px rgba(0, 0, 0, .08); padding: 1rem;">
          <span v-if="otherArticle != []" style="font-weight: bold;">
            Similar Answer:
          </span>
          <span v-if="otherArticle == []">
            No Similar Answer!
          </span>
          <div v-html="otherArticle"></div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>


<script>
import mixin from "@/mixins/page.js";
export default {
  mixins: [mixin],
  data() {
    return {
      list: [],
      similarityResult: null,
      experimentalTopic: null,
      author: null,
      reportTime: null,
      experimentalReportId: null,
      teacherName: null,
      similarity: 0,
      originArticle: '',
      subOriginArticle: '',
      otherArticle: [],
      subOtherArticle: [],
      tempOtherArticle: [],
      colors: ['red', 'blue', 'green', 'purple', 'orange', 'pink', 'cyan', 'magenta', 'lime', 'maroon', 'olive', 'chocolate', 'teal', 'navy', 'gold'],
      color_index: 0,
      flat: false,
      url_get_list: "~/api/experimental_report/get_list?like=0",
      buttonDisable: false
    };
  },
  created() {
    this.similarityResult = this.$route.query.result;
    this.experimentalTopic = this.$route.query.experimental_topic
    this.author = this.$route.query.author
    this.reportTime = this.timestampToDate(this.$route.query.reportTime)
    this.experimentalReportId = this.$route.query.experimentalReportId
    this.teacherName = this.$route.query.teacherName
    this.$router.push('/similarity-result')
  },
  methods:{
    getList(){
      this.buttonDisable = true
      for(let i = 0 ; i < this.list.length; i++){
        if(this.list[i].experimental_report_id == this.experimentalReportId){
          this.originArticle = this.list[i].experimental_solution
        }else{
          this.tempOtherArticle.push({[this.list[i].experimental_report_id.toString()]: this.list[i].experimental_solution})
        }
      }
      this.findHighestSimilarity()
    },
    timestampToDate(timestamp) {
      const date = new Date(timestamp); // 创建 Date 对象
      const year = date.getFullYear(); // 获取年份
      const month = (date.getMonth() + 1).toString().padStart(2, '0'); // 获取月份，月份是从 0 开始的，所以需要加1
      const day = date.getDate().toString().padStart(2, '0'); // 获取日

      return `${year}-${month}-${day}`; // 使用模板字符串返回格式化的日期
    },
    findHighestSimilarity(){
      console.log(this.similarityResult);
      var articleSimilarity = this.similarityResult.split('&')
      const ids = [];
      const values = [];

      articleSimilarity.forEach(segment => {
        const [id, value] = segment.split('?');
        ids.push(parseInt(id));  // 将id转换为整数
        values.push(value);
      });
      for(let j = 0 ; j < ids.length; j++){
        for(let i = 0 ; i < this.tempOtherArticle.length; i++){
          if(this.tempOtherArticle[i][ids[j]] != undefined){
            this.otherArticle.push(this.tempOtherArticle[i][ids[j]])
          }
        }
      }
      

      var hightestSimilarity = values
      console.log('hightestSimilarity', hightestSimilarity);
      hightestSimilarity = hightestSimilarity.map(group =>{
        return group.split(',').map(item => {
          return item.split('_').map(part => {
            return parseFloat(part) 
          })
        })
      })
      var color = this.colors[this.color_index++]
      console.log('hightestSimilarity', hightestSimilarity);
      var similarityList = hightestSimilarity[0].slice().map(item => {
        return [...item, color, 0]
      })
      console.log('similarityList', similarityList.slice());
      for(let group = 1; group < hightestSimilarity.length; group++){
        color = this.colors[this.color_index++] // 当前颜色
        // flat 当前颜色是否已被使用
        for(let item = 0; item < hightestSimilarity[group].length; item++){
          if(hightestSimilarity[group][item][0] == similarityList[item][0]){
            if(hightestSimilarity[group][item][2] > similarityList[item][2]){
              similarityList[item] = [...hightestSimilarity[group][item], color, group]
              this.flat = true
            }
          }
        }
        if(this.flat){
          this.flat = false
        }else{
          this.color_index--
        }
      }
      console.log('similarityList', similarityList);
      this.getSubOriginArticle(similarityList)
      this.getSubOtherArticle(similarityList)
    },
    getSubOriginArticle(similarityList){
      this.subOriginArticle = this.extractParagraphs(this.originArticle)
      // console.log('this.subOriginArticle', this.subOriginArticle);
      // 重复段落的索引
      var i = 1
      var color
      for(let index = 0; index < this.subOriginArticle.length; index++){
        // 调参数
        if(similarityList[index][2] <= 20){
          this.subOriginArticle[index] = `<p style="display: inline; font-size: 14px">${this.subOriginArticle[index]}</p>`
          continue
        }
        color = similarityList[index][3]
        this.similarity += this.subOriginArticle[index].length / this.originArticle.length * similarityList[index][2]
        console.log(this.subOriginArticle[index].length, this.originArticle.length);
        this.subOriginArticle[index] = `<p style="color:${color}; display: inline; font-size: 14px">${this.subOriginArticle[index]}[${i++}]</p>`
        // console.log('this.subOriginArticle[index]', this.subOriginArticle[index]);
      }
      this.originArticle = this.subOriginArticle.join('<br/>')
      this.similarity = Math.floor(this.similarity)
      this.similarity = this.similarity > 100 ? 100 : this.similarity
      this.similarity = this.similarity < 0 ? 0 : this.similarity
    },
    getSubOtherArticle(similarityList){
      var similarSentence = ''
      // 重复段落的索引
      var i = 1
      this.subOtherArticle = this.otherArticle.map(item => this.extractParagraphs(item))
      console.log('subOtherArticle', this.subOtherArticle);
      for(let index = 0; index < similarityList.length; index++){
        // 调参数
        if(similarityList[index][2] <= 20){
          continue
        }
        let articleIndex = similarityList[index][4]
        let sentenceIndex = similarityList[index][1]
        similarSentence += `<p style="color:${similarityList[index][3]}; font-size: 12px">${this.subOtherArticle[articleIndex][sentenceIndex]}[${i++}] ${similarityList[index][2]}%</p>`
        // console.log(similarityList[index]);
      }
      this.otherArticle = similarSentence
      
      // console.log('similarSentence', similarSentence);
    },
    extractParagraphs(htmlString) {
      const regex = /<p>(.*?)<\/p>/gs;
      let paragraphs = [];
      let match;
      while ((match = regex.exec(htmlString)) !== null) {
          paragraphs.push(match[1].trim());
      }
      return paragraphs;
    }
  }
}
</script>
<style scoped>
</style>