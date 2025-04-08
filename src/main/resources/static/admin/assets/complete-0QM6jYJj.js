import{e as B}from"./examPaperAnswer-n-eSkrym.js";import{d as F,H as L,v as y,r as c,e as O,j as l,o as d,c as g,a,w as o,m,F as z,C as A,n as M,u as R,t as U,l as H,I as G,k as J,_ as Q}from"./index-DXJQNOID.js";import{u as W}from"./enumitem-j20-ZoDl.js";const X={class:"list-common-table"},Y={class:"table-container"},Z="id",ee="top",te=!0,ae=F({__name:"complete",setup(oe){const _=L(),f=W(),w=y(()=>f.exam.examPaper.paperTypeEnum),s=c({subjectId:void 0}),v=c([]),C=c(0),i=c(!1),h=c(),x=y(()=>_.subjects),S=[{title:"ID",colKey:"id",width:100},{title:"试卷名称",colKey:"paperName",ellipsis:{theme:"light"}},{title:"试卷类型",colKey:"paperType",width:150,cell:(t,{row:e})=>f.enumFormat(w.value,e.paperType)},{title:"用户名称",colKey:"userName",width:100,ellipsis:{theme:"light"}},{title:"得分",colKey:"userScore",width:80,cell:(t,{row:e})=>`${e.userScore}分/${e.paperScore}分`},{title:"题目对错",colKey:"questionCorrect",width:80,cell:(t,{row:e})=>`${e.questionCorrect}/${e.questionCount}`},{title:"耗时",colKey:"doTime",width:120},{title:"操作时间",colKey:"createTime",width:200},{align:"left",fixed:"right",width:160,colKey:"op",title:"操作"}],j=t=>{const e=G.resolve({path:"/answer/completeRead",query:{id:t}});window.open(e.href,"_blank")},n=c({defaultPageSize:20,total:100,defaultCurrent:1}),k=t=>{var e;(e=t.pagination)!=null&&e.current&&(n.value.defaultCurrent=t.pagination.current,u())},u=async()=>{i.value=!0;try{const t={...s.value,pageIndex:n.value.defaultCurrent,pageSize:n.value.defaultPageSize};Object.keys(t).forEach(e=>{t[e]===""&&(t[e]=null)}),await B.completeList(t).then(e=>{v.value=e.list,C.value=e.total,n.value={...n.value,total:e.total}})}catch(t){console.log(t)}finally{i.value=!1}},I=()=>{Object.keys(s.value).forEach(t=>{s.value[t]===""&&(s.value[t]=null)}),n.value.defaultCurrent=1,u()},K=()=>{n.value.defaultCurrent=1,u()};return O(()=>{_.initSubject(()=>{h.value=x.value}),u()}),(t,e)=>{const E=l("t-option"),T=l("t-select"),q=l("t-form-item"),p=l("t-col"),b=l("t-row"),N=l("t-button"),D=l("t-form"),V=l("t-link"),P=l("t-space"),$=l("t-table");return d(),g("div",X,[a(D,{ref:"form",data:s.value,"label-width":80,colon:"",onReset:K,onSubmit:I},{default:o(()=>[a(b,null,{default:o(()=>[a(p,{span:10},{default:o(()=>[a(b,{gutter:[24,24]},{default:o(()=>[a(p,{span:4},{default:o(()=>[a(q,{label:"学科",name:"subjectId"},{default:o(()=>[a(T,{modelValue:s.value.subjectId,"onUpdate:modelValue":e[0]||(e[0]=r=>s.value.subjectId=r),class:"form-item-content",placeholder:"请选择学科",clearable:""},{default:o(()=>[e[1]||(e[1]=m(" > ")),(d(!0),g(z,null,A(h.value,r=>(d(),J(E,{key:r.id,label:r.name,value:Number(r.id)},null,8,["label","value"]))),128))]),_:1},8,["modelValue"])]),_:1})]),_:1})]),_:1})]),_:1}),a(p,{span:2,class:"operation-container"},{default:o(()=>[a(N,{theme:"primary",type:"submit"},{default:o(()=>[m(M(R(U)("components.commonTable.query")),1)]),_:1})]),_:1})]),_:1})]),_:1},8,["data"]),H("div",Y,[a($,{data:v.value,columns:S,"row-key":Z,"vertical-align":ee,hover:te,pagination:n.value,loading:i.value,onChange:k},{op:o(r=>[a(P,null,{default:o(()=>[a(V,{theme:"primary",onClick:le=>j(r.row.id)},{default:o(()=>e[2]||(e[2]=[m(" 查看试卷 ")])),_:2},1032,["onClick"])]),_:2},1024)]),_:1},8,["data","pagination","loading"])])])}}}),ce=Q(ae,[["__scopeId","data-v-7d5af7b4"]]);export{ce as default};
