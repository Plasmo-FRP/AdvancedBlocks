# AdvancedBlocks
Improve blocks, fix game bugs

**Config File**

```yaml

doorFixer:
  enabled: true # true or false

boatFixer:
  enabled: true # true or false

blocksForCancel:
  - NOTEBLOCK
  - COMMAND_BLOCK

messageOnCancel:
  enabled: true # true or false
  message: "##AAAAAA> #ED4343Блок временно отключён на сервере."
  #%block% Cancel block

mudBlock:
  effectLevel: 2
  blockList:
    - DIRT
    - SAND

dropEvent:
  enabled: true
  splitMultiplier: 2
  maxMinusValue: 10
  minMinusValue: 0
  items:
    - WHEAT_SEEDS
    - PUMPKIN_SEEDS
    - MELON_SEEDS
    - BEETROOT_SEEDS
    
```
