
# WearPri: LLM-based Privacy Compliance Estimation and Responsible Entities Identification in Wearable Apps
## 1. Citation

If you use WearPri results, please cite the following information. Thank you.
### Paper Link [WearPri](https://)
```bibtex

```
## 2. Introduction

**WearPri** is a pre-release privacy auditing framework designed for wearable companion apps in the Wear OS ecosystem. It aims to analyze APKs before public release and estimate whether an app complies with its declared data-sharing behavior. In addition, WearPri identifies the entity potentially responsible for privacy violations, which may be either the app developer or an integrated third-party service (TPS) provider.

Unlike post-release approaches that depend on app execution and network traffic observation, WearPri performs its analysis statically. It combines taint analysis, APK decompilation, Manifest analysis, RAG-based code summarization, and GraphRAG to support two prediction tasks:

- Data-sharing compliance estimation
- Responsible entity identification

Overall, WearPri supports app stores and auditors in proactively screening wearable companion apps for privacy risks before release.


WearPri architecture illustrated as figure below
<img src="https://github.com/research-mobile-security/WearPri/blob/main/project-image/figure-architecture-2.png">

