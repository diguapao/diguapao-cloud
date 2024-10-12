def bubble_sort(nums):
    n = len(nums)
    for i in range(n - 1):
        for j in range(n - 1 - i):
            if nums[j] > nums[j + 1]:
                # 交换
                nums[j], nums[j + 1] = nums[j + 1], nums[j]
